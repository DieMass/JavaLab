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
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static java.util.Arrays.asList;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(value = {"die.mass.entities","die.mass.hateoas.entities"})
public class HateoasApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(HateoasApplication.class, args);
		CustomSetupRepository customerRepository = context.getBean(CustomSetupRepository.class);
		CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
		CpuRepository cpuRepository = context.getBean(CpuRepository.class);
		SocketRepository socketRepository = context.getBean(SocketRepository.class);
		MotherBoardRepository motherBoardRepository = context.getBean(MotherBoardRepository.class);
		GpuRepository gpuRepository = context.getBean(GpuRepository.class);
		Company intelCompany = Company.builder().name("intel").build();
		Company amdCompany = Company.builder().name("amd").build();
		companyRepository.saveAll(asList(intelCompany, amdCompany));
		Socket intelSocket = Socket.builder().name("intelSocket").build();
		Socket amdSocket = Socket.builder().name("amdSocket").build();
		socketRepository.saveAll(asList(intelSocket, amdSocket));
		Cpu amdCpu = Cpu.builder().series("ultra321")
				.socket(amdSocket)
				.company(amdCompany)
				.build();
		Cpu intelCpu = Cpu.builder().series("xeon228")
				.socket(intelSocket)
				.company(intelCompany)
				.build();
		cpuRepository.saveAll(asList(amdCpu, intelCpu));
		MotherBoard intelMoBo = MotherBoard.builder().name("megaMoBo").socket(intelSocket).company(intelCompany).build();
		MotherBoard amdMoBo = MotherBoard.builder().name("ultraMoBo").socket(amdSocket).company(amdCompany).build();
		motherBoardRepository.saveAll(asList(intelMoBo, amdMoBo));
		GraphicsCardBase amdGpu = GraphicsCardBase.builder().name("amdGpu").company(amdCompany).build();
		GraphicsCardBase intelGpu = GraphicsCardBase.builder().name("intelGpu").company(intelCompany).build();
		gpuRepository.saveAll(asList(amdGpu, intelGpu));
		CustomSetup intelSetup = CustomSetup.builder().cpu(intelCpu).graphicsCardBase(intelGpu).motherBoard(intelMoBo).status(Status.DRAFT).build();
		CustomSetup amdSetup = CustomSetup.builder().cpu(amdCpu).graphicsCardBase(amdGpu).motherBoard(amdMoBo).status(Status.PUBLISH).build();
		customerRepository.saveAll(asList(intelSetup, amdSetup));
	}
}
