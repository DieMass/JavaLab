package project.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.dto.devices.*;
import project.services.devices.GpuService;

@RestController
@RequestMapping("/api/gpus")
public class GpuRestController {

	@Autowired
	private GpuService gpuService;

	@GetMapping
	@PreAuthorize("permitAll()")
	public ResponseGpusDto getBySocket(@RequestParam(value = "pciName", defaultValue = "") String pciName) {
		return ResponseGpusDto.builder().data(GpuDto.from(gpuService.getByPciName(pciName))).build();
	}

	@GetMapping("/byId")
	@PreAuthorize("permitAll()")
	public ResponseGpuDto getById(@RequestParam(value = "id", defaultValue = "1") Long id) {
		return ResponseGpuDto.builder().data(GpuDto.from(gpuService.find(id))).build();
	}
}
