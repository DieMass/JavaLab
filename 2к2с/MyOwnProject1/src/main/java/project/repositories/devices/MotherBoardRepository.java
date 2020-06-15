package project.repositories.devices;

import project.models.devices.motherboard.MotherBoard;
import project.repositories.general.CrudRepository;

import java.util.List;

public interface MotherBoardRepository extends CrudRepository<MotherBoard, Long> {

	List<MotherBoard> findBySocket(String socketName);
}


