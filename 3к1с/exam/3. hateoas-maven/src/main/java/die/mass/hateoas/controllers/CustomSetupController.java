package die.mass.hateoas.controllers;

import die.mass.hateoas.services.CustomSetupService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@AllArgsConstructor
public class CustomSetupController {

	private final CustomSetupService customSetupService;

	@RequestMapping(value = "/customSetups/{customSetup-id}/publish", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?> publish(@PathVariable("customSetup-id") Long customSetupId) {
		return ResponseEntity.ok(EntityModel.of(customSetupService.update(customSetupId)));
	}
}
