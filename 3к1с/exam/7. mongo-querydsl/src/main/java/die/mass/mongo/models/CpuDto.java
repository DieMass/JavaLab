package die.mass.mongo.models;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CpuDto {

	private String series;
	private Integer cores;
	private String socketName;
}
