package project.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.MotherBoard;
import project.models.devices.cpu.Cpu;
import project.models.devices.others.Gpu;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Setup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long account;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Cpu cpu;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private MotherBoard motherBoard;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Gpu gpu;

}
