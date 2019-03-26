package spark.model;

import lombok.Builder;
import lombok.Data;
import scala.Serializable;

import java.time.LocalDateTime;

@Data
@Builder
public class Trip implements Serializable {
    private String id;
    private String city;
    private Integer distance;
    private LocalDateTime date;
}
