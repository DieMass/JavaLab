package project.repositories;

import project.models.cpu.Cpu;

import java.util.List;

public interface CpuRepository extends CrudRepository<Cpu, Long> {

	List<Cpu> findAllWithPagination(Integer size, Integer page, String sort);
}
