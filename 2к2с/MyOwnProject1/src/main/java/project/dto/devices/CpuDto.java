package project.dto.devices;

import lombok.*;
import project.models.devices.cpu.Cpu;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CpuDto {

	private Long id;
	private String series;
	private Integer cores;
	private Integer threads;
	private String company;
	private String family;
	private String line;
	private String socket;
	private Integer lithography;
	private Integer tdp;
	private Integer baseClock;
	private Integer maxClockSingleCore;

	public static CpuDto from(Cpu cpu) {
		return CpuDto.builder()
				.id(cpu.getId())
				.series(cpu.getSeries())
				.cores(cpu.getCores())
				.threads(cpu.getThreads())
				.company(cpu.getCompany().getName())
				.family(cpu.getFamily().getName())
				.line(cpu.getLine().getName())
				.socket(cpu.getSocket().getName())
				.lithography(cpu.getLithography())
				.tdp(cpu.getTdp())
				.baseClock(cpu.getBaseClock())
				.maxClockSingleCore(cpu.getMaxClockSingleCore())
				.build();
	}

	public static List<CpuDto> from(List<Cpu> cpus) {
		return cpus.stream().map(CpuDto::from).collect(Collectors.toList());
	}

}
