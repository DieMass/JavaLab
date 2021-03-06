package project.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.dto.devices.CpuDto;
import project.dto.devices.ResponseCpuDto;
import project.dto.devices.ResponseCpusDto;
import project.models.devices.cpu.Cpu;
import project.services.devices.CpuService;

@RestController
@RequestMapping("/api/cpus")
public class CpuRestController {

	@Autowired
	private CpuService cpuService;

	@GetMapping("/list")
	@PreAuthorize("permitAll()")
	public ResponseCpusDto doGet(@RequestParam(value = "page", defaultValue = "1") Integer page,
								 @RequestParam(value = "size", defaultValue = "50") Integer size,
								 @RequestParam(value = "sort", defaultValue = "id") String sort) {
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		return ResponseCpusDto.builder().data(CpuDto.from(cpuService.getAll(size, page, sort))).build();
	}

	@GetMapping
	@PreAuthorize("permitAll()")
	public ResponseCpusDto getByField(@RequestParam(value = "socketName", defaultValue = "") String socketName) {
		return ResponseCpusDto.builder().data(CpuDto.from(cpuService.getBySocketName(socketName))).build();
	}

	@GetMapping("/byId")
	@PreAuthorize("permitAll()")
	public ResponseCpuDto getById(@RequestParam(value = "id", defaultValue = "1") Long id) {
		return ResponseCpuDto.builder().data(CpuDto.from(cpuService.find(id))).build();

	}

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseCpuDto doPost(@RequestBody CpuDto cpuDto) {
		Cpu cpu = cpuService.saveCpu(cpuDto);
		return ResponseCpuDto.builder()
				.data(CpuDto.from(cpu))
				.build();
	}

	@DeleteMapping("/delete/{cpu-id}")
	@PreAuthorize("isAuthenticated()")
	public void doDelete(@PathVariable(name = "cpu-id") Long id) {
		cpuService.delete(id);
	}

	@PutMapping("/update/{cpu-id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseCpuDto doPut(@PathVariable(name = "cpu-id") Long id, @RequestBody CpuDto cpuDto) {
		return ResponseCpuDto.builder().data(CpuDto.from(cpuService.update(cpuDto, id))).build();
	}
}
