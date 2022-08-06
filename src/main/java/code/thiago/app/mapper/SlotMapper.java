package code.thiago.app.mapper;

import code.thiago.app.dto.SlotDTO;
import code.thiago.app.model.Slot;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlotMapper {

    public SlotDTO mapEntityToDTO(Slot slot) {
        return new SlotDTO(slot.getStartDateTime(), slot.getEndDateTime());
    }

    public List<SlotDTO> mapEntitiesToDTOs(List<Slot> slots) {
        return slots.stream()
                .map(s -> new SlotDTO(s.getStartDateTime(), s.getEndDateTime()))
                .collect(Collectors.toList());
    }
}
