package code.thiago.app.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SlotsCreationDTO {

    /**
     * In real life 'personId' should be retrieved from the logged user, not from the request.
     * Unless you have a more complex logic with roles and permissions, so a user could create slots
     * for another user or something...
     */
    private String personId;
    private List<SlotIntervalDTO> slots = new ArrayList<>();
}
