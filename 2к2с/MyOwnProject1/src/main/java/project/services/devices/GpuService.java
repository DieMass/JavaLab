package project.services.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.devices.others.Gpu;
import project.repositories.devices.GpuRepository;

import java.util.List;

@Component
public class GpuService {

	@Autowired
	private GpuRepository gpuRepository;

	public List<Gpu> getByPciName(String socketName) {
		return socketName.equals("") ? gpuRepository.findAll() : gpuRepository.findByPcieName(socketName);
	}

	public Gpu find(Long id) {
		return gpuRepository.findById(id).orElse(null);
	}

}
