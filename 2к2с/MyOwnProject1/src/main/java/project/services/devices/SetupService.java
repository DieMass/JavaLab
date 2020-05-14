package project.services.devices;

import project.models.user.Setup;

import java.util.List;

public interface SetupService {

	List<Setup> getAllSetupsForUser(Long userId);
	void save(Setup setup);
	boolean find(Setup setup);


}
