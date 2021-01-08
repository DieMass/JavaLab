package die.mass.mongo.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CpuDto {

	private String series;
	private Integer cores;
	private String socketName;
}