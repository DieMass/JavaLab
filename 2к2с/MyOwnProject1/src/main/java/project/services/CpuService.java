package project.services;

import project.dto.CpuDto;
import project.models.cpu.Cpu;

import java.util.List;

public interface CpuService {

	List<Cpu> getAll();
	List<Cpu> getAll(Integer size, Integer page, String sort);
	Cpu saveCpu(CpuDto cpuDto);
	void delete(Long id);

	Cpu update(CpuDto cpuDto, Long id);
}
