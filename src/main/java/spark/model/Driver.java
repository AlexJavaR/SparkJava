package spark.model;

import lombok.Builder;
import lombok.Data;
import scala.Serializable;

import java.time.LocalDateTime;

@Data
@Builder
public class Driver implements Serializable {
    private String id;
    private String name;
    private String address;
    private String email;
}
