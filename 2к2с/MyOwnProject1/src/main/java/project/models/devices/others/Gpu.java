package project.models.devices.others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.devices.adapters.PCIe;
import project.models.devices.general.Company;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Gpu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Company company;
	@ManyToOne
	private PCIe pcie;
	private Integer memorySize;
	private Integer baseClock;
	private Integer tdp;
	private String memoryType;
	private String memoryInterface;


}

