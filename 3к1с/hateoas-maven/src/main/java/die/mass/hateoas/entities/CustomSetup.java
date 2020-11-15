package die.mass.hateoas.entities;

import die.mass.entities.cpu.Cpu;
import die.mass.entities.gpu.GraphicsCardBase;
import die.mass.entities.motherboard.MotherBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Stack;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CustomSetup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	@ManyToOne(cascade = CascadeType.MERGE)
	private Cpu cpu;
	@ManyToOne(cascade = CascadeType.MERGE)
	private MotherBoard motherBoard;
	@ManyToOne(cascade = CascadeType.MERGE)
	private GraphicsCardBase graphicsCardBase;
	private Status status;

	public void publish() {
		if(status.equals(Status.DRAFT)) status = Status.PUBLISH;
		else throw new IllegalArgumentException();
	}

}
