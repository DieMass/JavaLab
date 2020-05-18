package project.repositories.devices;


import org.springframework.data.repository.CrudRepository;
import project.models.devices.others.Gpu;

import java.util.List;

public interface GpuRepository extends CrudRepository<Gpu, Long> {

	List<Gpu> findByPcieName(String pciName);
	List<Gpu> findAll();
}
