package project.dto.devices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SetupStringDto {

	private String cpuName;
	private String moBoName;
	private String gpuName;

}
