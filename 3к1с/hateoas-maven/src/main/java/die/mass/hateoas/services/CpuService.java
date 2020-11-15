package die.mass.hateoas.services;

import die.mass.entities.cpu.Cpu;


public interface CpuService {

	Cpu changeSeries(String before, String after);

}
