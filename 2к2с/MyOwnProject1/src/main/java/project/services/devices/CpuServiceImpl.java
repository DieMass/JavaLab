package project.services.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.dto.devices.CpuDto;
import project.models.devices.adapters.Socket;
import project.models.devices.cpu.Cpu;
import project.models.devices.cpu.Family;
import project.models.devices.cpu.Line;
import project.models.devices.general.Company;
import project.repositories.devices.CpuRepository;
import project.repositories.devices.UniversalEntityRepository;

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
	public List<Cpu> getBySocketName(String socketName) {
		return socketName.equals("") ? cpuRepository.findAll() : cpuRepository.findBySocket(socketName);
	}

	@Override
	public Cpu saveCpu(CpuDto cpuDto) {
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

	@Override
	public Cpu find(Long id) {
		return cpuRepository.find(id).orElse(null);
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
