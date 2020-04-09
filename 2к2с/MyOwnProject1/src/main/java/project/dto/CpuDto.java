package project.dto;

import lombok.*;
import project.models.cpu.Cpu;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CpuDto {

	private long id;
	private String series;
	private int cores;
	private int threads;
	private String company;
	private String family;
	private String line;
	private String socket;
	private int lithography;
	private int tdp;
	private int baseClock;
	private int maxClockSingleCore;

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
