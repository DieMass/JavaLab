package project.repositories.devices;

import project.models.devices.cpu.Cpu;
import project.repositories.general.CrudRepository;

import java.util.List;

public interface CpuRepository extends CrudRepository<Cpu, Long> {

	List<Cpu> findAllWithPagination(Integer size, Integer page, String sort);
}
