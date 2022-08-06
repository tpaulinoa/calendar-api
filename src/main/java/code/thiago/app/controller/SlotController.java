package code.thiago.app.controller;

import code.thiago.app.dto.SlotDTO;
import code.thiago.app.dto.SlotsAvailabilitySearchDTO;
import code.thiago.app.dto.SlotsCreationDTO;
import code.thiago.app.interfaces.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/slot", produces = MediaType.APPLICATION_JSON_VALUE)
public class SlotController {

    private final SlotService slotService;

    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping
    public ResponseEntity<List<SlotDTO>> createSlots(@RequestBody SlotsCreationDTO slotsCreationDTO) {
        List<SlotDTO> slots = slotService.saveSlots(slotsCreationDTO);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(slots);
    }

    @PostMapping(value = "/availability")
    public ResponseEntity<List<SlotDTO>> findAvailability(@RequestBody SlotsAvailabilitySearchDTO slotsAvailabilitySearchDTO) {
        List<SlotDTO> slots = slotService.findAvailableSlots(slotsAvailabilitySearchDTO);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(slots);
    }
}
