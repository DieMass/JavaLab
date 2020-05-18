package project.dto.devices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.devices.others.Gpu;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GpuDto {

	private Long id;
	private String name;
	private String company;
	private String pci;
	private Integer memorySize;
	private Integer baseClock;
	private Integer tdp;
	private String memoryType;
	private String memoryInterface;

	public static GpuDto from(Gpu gpu) {
		return GpuDto.builder()
				.id(gpu.getId())
				.name(gpu.getName())
				.company(gpu.getCompany().getName())
				.tdp(gpu.getTdp())
				.baseClock(gpu.getBaseClock())
				.memoryInterface(gpu.getMemoryInterface())
				.memorySize(gpu.getMemorySize())
				.memoryType(gpu.getMemoryType())
				.pci(gpu.getPcie().getName())
				.build();
	}

	public static List<GpuDto> from(List<Gpu> gpus) {
		return gpus.stream().map(GpuDto::from).collect(Collectors.toList());
	}

}
