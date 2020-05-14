package project.services.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.user.Setup;
import project.repositories.devices.SetupRepository;

import java.util.List;

@Component
public class SetupServiceImpl implements SetupService {

	@Autowired
	private SetupRepository setupRepository;

	@Override
	public List<Setup> getAllSetupsForUser(Long userId) {
		return setupRepository.getAllByAccount(userId);
	}

	@Override
	public void save(Setup setup) {
		setupRepository.save(setup);
	}

	@Override
	public boolean find(Setup setup) {
		return setupRepository.find(setup.getId()).isPresent();
	}


}
