package code.thiago.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotDTO {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a", timezone = "UTC")
    @Column(name = "start_datetime")
    private Instant startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a", timezone = "UTC")
    @Column(name = "end_datetime")
    private Instant endDateTime;
}
