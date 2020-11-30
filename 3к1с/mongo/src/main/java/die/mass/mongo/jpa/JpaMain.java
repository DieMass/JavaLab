package die.mass.mongo.jpa;

import die.mass.mongo.jpa.repositories.CpuRepository;
import die.mass.mongo.models.Cpu;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JpaMain {

	public static void main(String[] args) {
		var context = new AnnotationConfigApplicationContext(RepositoriesConfig.class);
		var cpuRepository = context.getBean(CpuRepository.class);
		var cpu = Cpu.builder().series("227best").cores(20).build();
		cpuRepository.save(cpu);
		var newCpu = cpuRepository.findBySeries("227best");
		newCpu.get().setSeries("226best");
		cpuRepository.save(newCpu.get());
		cpuRepository.delete(newCpu.get());
	}
}
