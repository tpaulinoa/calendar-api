package code.thiago.app.service;

import code.thiago.app.dto.SlotDTO;
import code.thiago.app.dto.SlotIntervalDTO;
import code.thiago.app.dto.SlotsCreationDTO;
import code.thiago.app.mapper.SlotMapper;
import code.thiago.app.model.Interviewer;
import code.thiago.app.repository.PersonRepository;
import code.thiago.app.repository.SlotRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SlotServiceIntegrationTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a yyyy-MM-dd");

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private SlotMapper slotMapper;

    @Autowired
    private SlotServiceImpl service;

    @BeforeAll
    void setUp() {
        personRepository.deleteAll();
        insertInterviewer("John", "john@example.com", "123123123");
    }

    @AfterAll
    void cleanTestDb() {
        personRepository.deleteAll();
    }

    private void insertInterviewer(String name, String email, String phone) {
        Interviewer interviewer = new Interviewer();
        interviewer.setName(name);
        interviewer.setEmail(email);
        interviewer.setPhone(phone);
        personRepository.saveAndFlush(interviewer);
    }

    @Test
    void saveSlots_ReturnsSlots_When_InputIsValid() {
        Interviewer interviewer = (Interviewer) personRepository.findAll().get(0);

        System.out.println("\n\n*******\n " + interviewer.getId() + "\n\n*******\n\n ");
        SlotsCreationDTO inputCreationDTO = new SlotsCreationDTO();
        inputCreationDTO.setPersonId(interviewer.getId());
        inputCreationDTO.setSlots(List.of(
                buildSlotIntervalDTO("10:00 AM", "2022-08-08", "02:00 PM", "2022-08-09")
        ));

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

    @Test
    void saveSlots_ReturnsSlots_When_InputIsValidAndDaysOfWeekIsSpecified() {
        Interviewer interviewer = (Interviewer) personRepository.findAll().get(0);
        System.out.println("\n\n---------\n " + interviewer.getId() + "\n\n---------\n\n ");
        SlotsCreationDTO inputCreationDTO = new SlotsCreationDTO();
        inputCreationDTO.setPersonId(interviewer.getId());

        /* A date interval for a whole week is given but the weekdays are specified,
         * so the slots will be created only for those days
         */
        inputCreationDTO.setSlots(List.of(
                buildSlotIntervalDTO("10:00 AM", "2022-08-08", "02:00 PM", "2022-08-12", List.of("WEDNESDAY", "FRIDAY"))
        ));

        List<SlotDTO> expectedSlots = Arrays.asList(
                SlotDTO.builder().startDateTime(toInstant("10:00 AM 2022-08-10")).endDateTime(toInstant("11:00 AM 2022-08-10")).build(),
                SlotDTO.builder().startDateTime(toInstant("11:00 AM 2022-08-10")).endDateTime(toInstant("12:00 PM 2022-08-10")).build(),
                SlotDTO.builder().startDateTime(toInstant("12:00 PM 2022-08-10")).endDateTime(toInstant("01:00 PM 2022-08-10")).build(),
                SlotDTO.builder().startDateTime(toInstant("01:00 PM 2022-08-10")).endDateTime(toInstant("02:00 PM 2022-08-10")).build(),

                SlotDTO.builder().startDateTime(toInstant("10:00 AM 2022-08-12")).endDateTime(toInstant("11:00 AM 2022-08-12")).build(),
                SlotDTO.builder().startDateTime(toInstant("11:00 AM 2022-08-12")).endDateTime(toInstant("12:00 PM 2022-08-12")).build(),
                SlotDTO.builder().startDateTime(toInstant("12:00 PM 2022-08-12")).endDateTime(toInstant("01:00 PM 2022-08-12")).build(),
                SlotDTO.builder().startDateTime(toInstant("01:00 PM 2022-08-12")).endDateTime(toInstant("02:00 PM 2022-08-12")).build()
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

    private Instant toInstant(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter).toInstant(ZoneOffset.UTC);
    }

}
