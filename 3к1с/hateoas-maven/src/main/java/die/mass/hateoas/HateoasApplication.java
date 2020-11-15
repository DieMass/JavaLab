package die.mass.hateoas;

import die.mass.entities.adapters.Socket;
import die.mass.entities.cpu.Cpu;
import die.mass.entities.general.Company;
import die.mass.entities.gpu.GraphicsCardBase;
import die.mass.entities.motherboard.MotherBoard;
import die.mass.hateoas.entities.CustomSetup;
import die.mass.hateoas.entities.Status;
import die.mass.hateoas.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static java.util.Arrays.asList;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(value = {"die.mass.entities","die.mass.hateoas.entities"})
public class HateoasApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(HateoasApplication.class, args);
		var customerRepository = context.getBean(CustomSetupRepository.class);
		var companyRepository = context.getBean(CompanyRepository.class);
		var cpuRepository = context.getBean(CpuRepository.class);
		var socketRepository = context.getBean(SocketRepository.class);
		var motherBoardRepository = context.getBean(MotherBoardRepository.class);
		var gpuRepository = context.getBean(GpuRepository.class);

		var intelCompany = Company.builder().name("intel").build();
		var amdCompany = Company.builder().name("amd").build();
		companyRepository.saveAll(asList(intelCompany, amdCompany));

		var intelSocket = Socket.builder().name("intelSocket").build();
		var amdSocket = Socket.builder().name("amdSocket").build();
		socketRepository.saveAll(asList(intelSocket, amdSocket));

		var amdCpu = Cpu.builder().series("ultra321")
				.socket(amdSocket)
				.company(amdCompany)
				.build();
		var intelCpu = Cpu.builder().series("xeon228")
				.socket(intelSocket)
				.company(intelCompany)
				.build();
		cpuRepository.saveAll(asList(amdCpu, intelCpu));

		var intelMoBo = MotherBoard.builder().name("megaMoBo").socket(intelSocket).company(intelCompany).build();
		var amdMoBo = MotherBoard.builder().name("ultraMoBo").socket(amdSocket).company(amdCompany).build();
		motherBoardRepository.saveAll(asList(intelMoBo, amdMoBo));

		var amdGpu = GraphicsCardBase.builder().name("amdGpu").company(amdCompany).build();
		var intelGpu = GraphicsCardBase.builder().name("intelGpu").company(intelCompany).build();
		gpuRepository.saveAll(asList(amdGpu, intelGpu));

		var intelSetup = CustomSetup.builder().cpu(intelCpu).graphicsCardBase(intelGpu).motherBoard(intelMoBo).status(Status.DRAFT).build();
		var amdSetup = CustomSetup.builder().cpu(amdCpu).graphicsCardBase(amdGpu).motherBoard(amdMoBo).status(Status.PUBLISH).build();
		customerRepository.saveAll(asList(intelSetup, amdSetup));

	}

}
