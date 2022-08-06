package code.thiago.app.interfaces;

import code.thiago.app.dto.SlotDTO;
import code.thiago.app.dto.SlotsAvailabilitySearchDTO;
import code.thiago.app.dto.SlotsCreationDTO;
import code.thiago.app.exceptions.PersonNotFoundException;

import java.util.List;

public interface SlotService {

    List<SlotDTO> saveSlots(SlotsCreationDTO slots) throws PersonNotFoundException;

    List<SlotDTO> findAvailableSlots(SlotsAvailabilitySearchDTO slotsAvailabilitySearchDTO);
}
