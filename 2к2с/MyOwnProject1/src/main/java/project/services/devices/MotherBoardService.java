package project.services.devices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.models.devices.motherboard.MotherBoard;
import project.repositories.devices.MotherBoardRepository;

import java.util.List;

@Component
public class MotherBoardService {

	@Autowired
	private MotherBoardRepository motherBoardRepository;

	public List<MotherBoard> getBySocketName(String socketName) {
		return socketName.equals("") ? motherBoardRepository.findAll() : motherBoardRepository.findBySocket(socketName);
	}

	public MotherBoard find(Long id) {
		return motherBoardRepository.find(id).orElse(null);
	}

}
