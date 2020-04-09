package project.models.motherboard;

import lombok.*;
import project.models.MotherBoard;
import project.models.cpu.Cpu;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@ToString(exclude = {"motherBoards", "cpus"})
public class Chipset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany(mappedBy = "chipset", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MotherBoard> motherBoards;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Cpu> cpus;
}