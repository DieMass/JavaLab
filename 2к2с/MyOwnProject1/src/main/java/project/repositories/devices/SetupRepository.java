package project.repositories.devices;

import project.models.user.Setup;
import project.repositories.general.CrudRepository;

import java.util.List;

public interface SetupRepository extends CrudRepository<Setup, Long> {

	List<Setup> getAllByAccount(Long userId);
}
