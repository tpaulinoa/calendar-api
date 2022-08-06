package code.thiago.app.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SlotsAvailabilitySearchDTO {

    private String candidateId;
    private List<String> interviewersIds = new ArrayList<>();;
}
