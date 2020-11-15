package die.mass.hateoas.services;

import die.mass.hateoas.entities.CustomSetup;
import die.mass.hateoas.repositories.CustomSetupRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomSetupServiceImpl implements CustomSetupService {

	private final CustomSetupRepository customSetupRepository;

	@Override
	public CustomSetup update(Long customSetupId) {
		var customSetup = customSetupRepository.findById(customSetupId).orElseThrow(IllegalArgumentException::new);
		customSetup.publish();
		customSetupRepository.save(customSetup);
		return customSetup;
	}
}