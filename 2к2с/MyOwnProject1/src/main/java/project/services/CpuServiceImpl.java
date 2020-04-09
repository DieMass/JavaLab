package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.CpuDto;
import project.models.adapters.Socket;
import project.models.cpu.Cpu;
import project.models.cpu.Family;
import project.models.cpu.Line;
import project.models.general.Company;
import project.repositories.CpuRepository;
import project.repositories.UniversalEntityRepository;

import java.util.List;
import java.util.Optional;

@Component
public class CpuServiceImpl implements CpuService {

	@Autowired
	private CpuRepository cpuRepository;
	@Autowired
	private UniversalEntityRepository universalEntityRepository;

	@Override
	public List<Cpu> getAll() {
		return cpuRepository.findAll();
	}

	@Override
	public List<Cpu> getAll(Integer size, Integer page, String sort) {
		return cpuRepository.findAllWithPagination(size, page, sort);
	}

	@Override
	public Cpu 	saveCpu(CpuDto cpuDto) {
		Cpu cpu = createFromCpuDto(cpuDto, null);
		cpuRepository.save(cpu);
		return cpu;
	}

	@Override
	public Cpu update(CpuDto cpuDto, Long id) {
		Optional<Cpu> inDB = cpuRepository.find(id);
		if (inDB.isPresent()) {
			Cpu cpu = createFromCpuDto(cpuDto, id);
			cpuRepository.update(cpu);
			return cpuRepository.find(id).get();
		}
		return Cpu.builder().build();
	}

	private Cpu createFromCpuDto(CpuDto cpuDto, Long id) {
		Optional<Line> lineCandidate = checkToNull(Line.class, cpuDto.getLine());
		Optional<Company> companyCandidate = checkToNull(Company.class, cpuDto.getCompany());
		Optional<Socket> socketCandidate = checkToNull(Socket.class, cpuDto.getSocket());
		Optional<Family> familyCandidate = checkToNull(Family.class, cpuDto.getFamily());
		Line line = getFromOptional(lineCandidate, Line.builder().name(cpuDto.getLine() == null ? "" : cpuDto.getLine()).build());
		Company company = getFromOptional(companyCandidate, Company.builder().name(cpuDto.getCompany() == null ? "" : cpuDto.getCompany()).build());
		Family family = getFromOptional(familyCandidate, Family.builder().name(cpuDto.getFamily() == null ? "" : cpuDto.getFamily()).build());
		Socket socket = getFromOptional(socketCandidate, Socket.builder().name(cpuDto.getSocket() == null ? "" : cpuDto.getSocket()).build());
		return Cpu.builder()
				.line(line)
				.company(company)
				.socket(socket)
				.family(family)
				.cores(cpuDto.getCores())
				.threads(cpuDto.getThreads())
				.lithography(cpuDto.getLithography())
				.tdp(cpuDto.getTdp())
				.series(cpuDto.getSeries())
				.baseClock(cpuDto.getBaseClock())
				.maxClockSingleCore(cpuDto.getMaxClockSingleCore())
				.id(id)
				.build();
	}

	@Override
	public void delete(Long id) {
		cpuRepository.delete(id);
	}

	private <T> T getFromOptional(Optional<T> candidate, T modelElse) {
		if (candidate.isPresent()) {
			return candidate.get();
		} else {
			universalEntityRepository.save(modelElse);
			return modelElse;
		}
	}

	private <T> Optional<T> checkToNull(Class<T> tClass, String field) {
		return field != null ? universalEntityRepository.findByName(tClass, field) : Optional.empty();
	}
}
