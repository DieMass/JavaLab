package project.models.cpu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@ToString(exclude = {"cpus"})
public class Family {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@JsonIgnore
	@OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cpu> cpus;

}
