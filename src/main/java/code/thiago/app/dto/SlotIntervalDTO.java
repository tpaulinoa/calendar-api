package code.thiago.app.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SlotIntervalDTO {

    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;
    private List<String> daysOfWeek = new ArrayList<>();
}
