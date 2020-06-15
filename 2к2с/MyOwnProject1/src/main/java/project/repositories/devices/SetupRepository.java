package project.repositories.devices;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import project.dto.devices.SetupStringDto;
import project.models.user.Setup;

import java.util.List;

public interface SetupRepository extends CrudRepository<Setup, Long> {

	@Query("select new project.dto.devices.SetupStringDto(CONCAT(setup.cpu.company.name, ' ', setup.cpu.family.name," +
			"' ', setup.cpu.line.name, ' ', setup.cpu.series), setup.motherBoard.name, setup.gpu.name ) " +
			"from Setup setup where setup.account = :userId")
	List<SetupStringDto> getStringSetupDtos(@Param("userId") Long userId);

	List<Setup> findAllByAccount(Long userId);
	void deleteByAccount(Long userId);

}


