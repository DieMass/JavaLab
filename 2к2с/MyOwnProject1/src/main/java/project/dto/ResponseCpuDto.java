package project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.devices.cpu.Cpu;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseCpuDto {

	private Cpu data;
}
