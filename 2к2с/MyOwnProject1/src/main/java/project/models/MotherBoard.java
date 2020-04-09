package project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.adapters.PCIe;
import project.models.adapters.Socket;
import project.models.general.Company;
import project.models.motherboard.Chipset;
import project.models.motherboard.FormFactor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class MotherBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Company company;
	@ManyToOne
	private Socket socket;
	@ManyToOne
	private Chipset chipset;
	@ManyToOne
	private FormFactor formFactor;
	@ManyToOne
	private PCIe pcie;
}
