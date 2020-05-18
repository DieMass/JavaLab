package project.dto.devices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SetupDto {

	private Long account;
	private Long cpu;
	private Long mobo;
	private Long gpu;
}
