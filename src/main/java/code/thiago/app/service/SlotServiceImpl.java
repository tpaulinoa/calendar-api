package code.thiago.app.service;

import code.thiago.app.dto.SlotDTO;
import code.thiago.app.dto.SlotsAvailabilitySearchDTO;
import code.thiago.app.dto.SlotsCreationDTO;
import code.thiago.app.dto.SlotIntervalDTO;
import code.thiago.app.exceptions.InvalidDateTimeException;
import code.thiago.app.exceptions.PersonNotFoundException;
import code.thiago.app.exceptions.SlotException;
import code.thiago.app.interfaces.SlotService;
import code.thiago.app.mapper.SlotMapper;
import code.thiago.app.model.Person;
import code.thiago.app.model.Slot;
import code.thiago.app.repository.PersonRepository;
import code.thiago.app.repository.SlotRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SlotServiceImpl implements SlotService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a yyyy-MM-dd");

    private final PersonRepository personRepository;
    private final SlotRepository slotRepository;
    private final SlotMapper slotMapper;

    @Autowired
    public SlotServiceImpl(PersonRepository personRepository, SlotRepository slotRepository, SlotMapper slotMapper) {
        this.personRepository = personRepository;
        this.slotRepository = slotRepository;
        this.slotMapper = slotMapper;
    }

    @SneakyThrows
    @Override
    public List<SlotDTO> saveSlots(SlotsCreationDTO slotsCreationDTO) {
        Person person = (Person) personRepository.findById(slotsCreationDTO.getPersonId()).orElseThrow(
                () -> new PersonNotFoundException(slotsCreationDTO.getPersonId()));

        List<SlotIntervalDTO> slotsIntervals = slotsCreationDTO.getSlots();
        if (slotsIntervals.isEmpty()) {
            throw new SlotException("The slots' list is empty.");
        }

        Set<Slot> slots = buildSlots(slotsIntervals, person);
        List<Slot> savedSlots = slotRepository.saveAll(slots);
        savedSlots.sort(Comparator.comparing(Slot::getStartDateTime));

        return slotMapper.mapEntitiesToDTOs(savedSlots);
    }

    @Override
    public List<SlotDTO> findAvailableSlots(SlotsAvailabilitySearchDTO slotsAvailabilitySearchDTO) {
        Set<String> participantsIds = new HashSet<>(Arrays.asList(slotsAvailabilitySearchDTO.getCandidateId()));
        participantsIds.addAll(slotsAvailabilitySearchDTO.getInterviewersIds());

        long count = personRepository.countByIdIn(participantsIds);
        if (count != participantsIds.size()) {
            throw new SlotException("One or more participants were not found.");
        }

        return slotRepository.findAvailableSlots(participantsIds, participantsIds.size());
    }

    private Set<Slot> buildSlots(List<SlotIntervalDTO> slotsIntervals, Person person) {
        Set<Slot> slots = new HashSet<>();
        slotsIntervals.forEach(slotDTO -> calculateAndFillSlots(slotDTO, slots, person));
        return slots;
    }

    private void calculateAndFillSlots(SlotIntervalDTO slotDTO, Set<Slot> slots, Person person) {
        // Parse dates and times
        LocalDateTime startDateTime = LocalDateTime.parse(
                slotDTO.getStartTime().trim() + " " + slotDTO.getStartDate().trim(), dateTimeFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(
                slotDTO.getEndTime().trim() + " " + slotDTO.getEndDate().trim(), dateTimeFormatter);

        // Check if datetimes are coherent and if they aren't "broken" (e.g. 09:30)
        if (startDateTime.isAfter(endDateTime)
            || startDateTime.getMinute() != 0 || endDateTime.getMinute() != 0) {
            throw new InvalidDateTimeException();
        }

        // Calculate slots
        LocalDateTime tempStart = null;
        LocalDateTime tempEnd = null;

        // Get dates (days) between the start date and end date
        List<LocalDate> dates = startDateTime.toLocalDate()
                .datesUntil(endDateTime.toLocalDate().plusDays(1)).collect(Collectors.toList());
        for(LocalDate date: dates) {

            // Check for any "repetition" rule
            if (!slotDTO.getDaysOfWeek().isEmpty() && !slotDTO.getDaysOfWeek().contains(date.getDayOfWeek().toString())) {
                continue;
            }

            // For each date (each day) sets the hour and minute according to the input startDateTime/endDateTime
            LocalDateTime startLocalDateTime = date.atTime(startDateTime.getHour(), startDateTime.getMinute());
            LocalDateTime endLocalDateTime = date.atTime(endDateTime.getHour(), endDateTime.getMinute());

            // Get how many hours is the difference between the datetimes
            long hoursDifference = startLocalDateTime.until(endLocalDateTime, ChronoUnit.HOURS);

            // Add a slot xTimes the ´hoursDifference´
            tempStart = startLocalDateTime;
            tempEnd = startLocalDateTime.plusHours(1);
            for (int i = 0; i < hoursDifference; i++) {
                slots.add(Slot.builder()
                        .startDateTime(tempStart.toInstant(ZoneOffset.UTC))
                        .endDateTime(tempEnd.toInstant(ZoneOffset.UTC))
                        .person(person)
                        .build());

                tempStart = tempEnd;
                tempEnd = tempEnd.plusHours(1);
            }
        }
    }
}
