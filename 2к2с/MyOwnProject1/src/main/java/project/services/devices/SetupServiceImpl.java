package project.services.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.dto.devices.SetupStringDto;
import project.models.user.Setup;
import project.repositories.devices.SetupRepository;

import java.util.List;

@Component
public class SetupServiceImpl implements SetupService {

	@Autowired
	private SetupRepository setupRepository;

	@Override
	public List<Setup> getAllSetupsForUser(Long userId) {
		return setupRepository.findAllByAccount(userId);
	}

	@Override
	public void save(Setup setup) {
		setupRepository.save(setup);
	}

	@Override
	public boolean find(Setup setup) {
		return setupRepository.findById(setup.getId()).isPresent();
	}

	@Override
	@Transactional
	public void deleteAll(Long userId) {
		setupRepository.deleteByAccount(userId);
	}

	@Override
	public List<SetupStringDto> getStringSetupDtos(Long userId) {
		return setupRepository.getStringSetupDtos(userId);
	}
}
