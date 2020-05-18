package project.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.config.UTF8Control;
import project.dto.devices.SetupDto;
import project.models.user.Setup;
import project.security.details.UserDetailsImpl;
import project.services.devices.CpuService;
import project.services.devices.GpuService;
import project.services.devices.MotherBoardService;
import project.services.devices.SetupService;

import java.util.Locale;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/api/setups")
public class SetupRestController {

	@Autowired
	private SetupService setupService;
	@Autowired
	private CpuService cpuService;
	@Autowired
	private MotherBoardService motherBoardService;
	@Autowired
	private GpuService gpuService;

	@PostMapping
	@PreAuthorize("permitAll()")
	public String doPost(@RequestBody SetupDto setupDto) {
		Setup setup = Setup.builder()
				.account(setupDto.getAccount())
				.cpu(cpuService.find(setupDto.getCpu()))
		.motherBoard(motherBoardService.find(setupDto.getMobo()))
				.gpu(gpuService.find(setupDto.getGpu()))
		.build();
		setupService.save(setup);
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle introLabels = ResourceBundle.getBundle("messages/messages", locale, new UTF8Control());
		return setupService.find(setup) ? introLabels.getString("setupRestController.true")
				: introLabels.getString("setupRestController.false");
	}

	@DeleteMapping
	@PreAuthorize("permitAll()")
	public void deleteAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
		Long userId = userDetails.getUserId();
		setupService.deleteAll(userId);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> getSetupsByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok().body(setupService.getStringSetupDtos(userId));
	}
}
