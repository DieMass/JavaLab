package project.services.devices;

import project.dto.devices.SetupStringDto;
import project.models.user.Setup;

import java.util.List;

public interface SetupService {

	List<Setup> getAllSetupsForUser(Long userId);
	void save(Setup setup);
	boolean find(Setup setup);
	void deleteAll(Long userId);
	List<SetupStringDto> getStringSetupDtos(Long userId);


}
