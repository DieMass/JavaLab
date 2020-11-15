package die.mass.hateoas.services;

import die.mass.entities.cpu.Cpu;
import die.mass.hateoas.repositories.CpuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@AllArgsConstructor
public class CpuServiceImpl implements CpuService {

	private final CpuRepository cpuRepository;

	@Override
	public Cpu changeSeries(String before, String after) {
		var cpu = cpuRepository.findBySeries(before);
		cpu.setSeries(after);
		cpu.setLaunchDate(LocalDate.now().toString());
		cpuRepository.save(cpu);
		return cpu;
	}
}
