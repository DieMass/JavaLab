package project.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.dto.devices.MoBoDto;
import project.dto.devices.ResponseMoBosDto;
import project.services.devices.MotherBoardService;

@RestController
@RequestMapping("/api/mobos")
public class MotherBoardRestController {

	@Autowired
	private MotherBoardService motherBoardService;

	@GetMapping("/bysocket")
	@PreAuthorize("permitAll()")
	public ResponseMoBosDto getBySocket(@RequestParam(value = "socketName", defaultValue = "") String socketName) {
		return ResponseMoBosDto.builder().data(MoBoDto.from(motherBoardService.getBySocketName(socketName))).build();
	}
}
