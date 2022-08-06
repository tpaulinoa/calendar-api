package code.thiago.app.service;

import code.thiago.app.dto.SlotDTO;
import code.thiago.app.dto.SlotIntervalDTO;
import code.thiago.app.dto.SlotsAvailabilitySearchDTO;
import code.thiago.app.dto.SlotsCreationDTO;
import code.thiago.app.exceptions.SlotException;
import code.thiago.app.mapper.SlotMapper;
import code.thiago.app.model.Interviewer;
import code.thiago.app.model.Slot;
import code.thiago.app.repository.PersonRepository;
import code.thiago.app.repository.SlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a yyyy-MM-dd");

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private SlotRepository slotRepository;

    @Autowired
    private SlotMapper slotMapper;

    @Autowired
    private SlotServiceImpl service;

    @Test
    void saveSlots_ReturnsSlots_When_InputIsValid() {
        String interviewerId = "123";
        SlotsCreationDTO inputCreationDTO = new SlotsCreationDTO();
        inputCreationDTO.setPersonId(interviewerId);
        inputCreationDTO.setSlots(List.of(
                buildSlotIntervalDTO("10:00 AM", "2022-08-08", "02:00 PM", "2022-08-09")
        ));

        Interviewer interviewer = new Interviewer();
        interviewer.setId(interviewerId);
        when(personRepository.findById(interviewerId)).thenReturn(Optional.of(interviewer));

        when(slotRepository.saveAll(any())).thenReturn(
                Arrays.asList(
                        Slot.builder().startDateTime(toInstant("10:00 AM 2022-08-08")).endDateTime(toInstant("11:00 AM 2022-08-08")).build(),
                        Slot.builder().startDateTime(toInstant("11:00 AM 2022-08-08")).endDateTime(toInstant("12:00 PM 2022-08-08")).build(),
                        Slot.builder().startDateTime(toInstant("12:00 PM 2022-08-08")).endDateTime(toInstant("01:00 PM 2022-08-08")).build(),
                        Slot.builder().startDateTime(toInstant("01:00 PM 2022-08-08")).endDateTime(toInstant("02:00 PM 2022-08-08")).build(),

                        Slot.builder().startDateTime(toInstant("10:00 AM 2022-08-09")).endDateTime(toInstant("11:00 AM 2022-08-09")).build(),
                        Slot.builder().startDateTime(toInstant("11:00 AM 2022-08-09")).endDateTime(toInstant("12:00 PM 2022-08-09")).build(),
                        Slot.builder().startDateTime(toInstant("12:00 PM 2022-08-09")).endDateTime(toInstant("01:00 PM 2022-08-09")).build(),
                        Slot.builder().startDateTime(toInstant("01:00 PM 2022-08-09")).endDateTime(toInstant("02:00 PM 2022-08-09")).build()
                )
        );

        List<SlotDTO> expectedSlots = Arrays.asList(
                SlotDTO.builder().startDateTime(toInstant("10:00 AM 2022-08-08")).endDateTime(toInstant("11:00 AM 2022-08-08")).build(),
                SlotDTO.builder().startDateTime(toInstant("11:00 AM 2022-08-08")).endDateTime(toInstant("12:00 PM 2022-08-08")).build(),
                SlotDTO.builder().startDateTime(toInstant("12:00 PM 2022-08-08")).endDateTime(toInstant("01:00 PM 2022-08-08")).build(),
                SlotDTO.builder().startDateTime(toInstant("01:00 PM 2022-08-08")).endDateTime(toInstant("02:00 PM 2022-08-08")).build(),

                SlotDTO.builder().startDateTime(toInstant("10:00 AM 2022-08-09")).endDateTime(toInstant("11:00 AM 2022-08-09")).build(),
                SlotDTO.builder().startDateTime(toInstant("11:00 AM 2022-08-09")).endDateTime(toInstant("12:00 PM 2022-08-09")).build(),
                SlotDTO.builder().startDateTime(toInstant("12:00 PM 2022-08-09")).endDateTime(toInstant("01:00 PM 2022-08-09")).build(),
                SlotDTO.builder().startDateTime(toInstant("01:00 PM 2022-08-09")).endDateTime(toInstant("02:00 PM 2022-08-09")).build()
        );

        List<SlotDTO> createdSlots = service.saveSlots(inputCreationDTO);

        // Compare each slot to make sure they were returned in the correct order
        assertEquals(expectedSlots.size(), createdSlots.size());
        assertEquals(expectedSlots.get(0), createdSlots.get(0));
        assertEquals(expectedSlots.get(1), createdSlots.get(1));
        assertEquals(expectedSlots.get(2), createdSlots.get(2));
        assertEquals(expectedSlots.get(3), createdSlots.get(3));
        assertEquals(expectedSlots.get(4), createdSlots.get(4));
        assertEquals(expectedSlots.get(5), createdSlots.get(5));
        assertEquals(expectedSlots.get(6), createdSlots.get(6));
        assertEquals(expectedSlots.get(7), createdSlots.get(7));
    }

    private SlotIntervalDTO buildSlotIntervalDTO(String startTime, String startDate, String endTime, String endDate) {
        return buildSlotIntervalDTO(startTime, startDate, endTime, endDate, new ArrayList<>());
    }

    private SlotIntervalDTO buildSlotIntervalDTO(String startTime, String startDate, String endTime, String endDate, List<String> daysOfWeek) {
        SlotIntervalDTO intervalDTO = new SlotIntervalDTO();
        intervalDTO.setStartTime(startTime);
        intervalDTO.setStartDate(startDate);
        intervalDTO.setEndTime(endTime);
        intervalDTO.setEndDate(endDate);
        intervalDTO.setDaysOfWeek(daysOfWeek);
        return intervalDTO;
    }

    @Test
    void findAvailableSlots_ReturnsSlots_When_ThereAreSlotsAvailable() {
        String candidateId = "candidate123";
        List<String> interviewersIds = Arrays.asList("interviewer123", "interviewer456");

        // Build search object
        SlotsAvailabilitySearchDTO searchSlotsDTO = new SlotsAvailabilitySearchDTO();
        searchSlotsDTO.setCandidateId(candidateId);
        searchSlotsDTO.setInterviewersIds(interviewersIds);

        // Mock the participants check
        Set<String> uniqueIds = new HashSet<>(interviewersIds);
        uniqueIds.add(candidateId);

        when(personRepository.countByIdIn(uniqueIds)).thenReturn(3L);

        // Mock result from SlotRepository
        List<SlotDTO> result = new ArrayList<>();
        result.add(new SlotDTO(toInstant("09:00 AM 2022-08-08"), toInstant("10:00 AM 2022-08-09")));

        when(slotRepository.findAvailableSlots(uniqueIds, uniqueIds.size())).thenReturn(result);

        // Call service and assert results
        List<SlotDTO> availableSlots = service.findAvailableSlots(searchSlotsDTO);
        assertEquals(1, availableSlots.size());
    }

    @Test
    void findAvailableSlots_ThrowsSlotException_When_SomeIdDoesNotExistsInDatabase() {
        String candidateId = "candidate123";
        List<String> interviewersIds = Arrays.asList("interviewer123", "interviewer456", "some-not-existent-id");

        // Build search object
        SlotsAvailabilitySearchDTO searchSlotsDTO = new SlotsAvailabilitySearchDTO();
        searchSlotsDTO.setCandidateId(candidateId);
        searchSlotsDTO.setInterviewersIds(interviewersIds);

        // Mock the participants check
        Set<String> uniqueIds = new HashSet<>(interviewersIds);
        uniqueIds.add(candidateId);

        when(personRepository.countByIdIn(uniqueIds)).thenReturn(2L);

        // Call service and assert exception
        SlotException slotException = assertThrows(SlotException.class, () -> service.findAvailableSlots(searchSlotsDTO));
        assertEquals("One or more participants were not found.", slotException.getMessage());
    }

    private Instant toInstant(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter).toInstant(ZoneOffset.UTC);
    }

}
