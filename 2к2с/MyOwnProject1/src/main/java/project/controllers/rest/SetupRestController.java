package project.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.config.UTF8Control;
import project.dto.devices.SetupDto;
import project.models.user.Setup;
import project.services.devices.CpuService;
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

	@PostMapping
	@PreAuthorize("permitAll()")
	public String doPost(@RequestBody SetupDto setupDto) {
		Setup setup = Setup.builder()
				.account(setupDto.getAccount())
				.cpu(cpuService.find(setupDto.getCpu()))
		.motherBoard(motherBoardService.find(setupDto.getMobo()))
		.build();
		setupService.save(setup);
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle introLabels = ResourceBundle.getBundle("messages/messages", locale, new UTF8Control());
		return setupService.find(setup) ? introLabels.getString("setupRestController.true")
				: introLabels.getString("setupRestController.false");

	}
}
