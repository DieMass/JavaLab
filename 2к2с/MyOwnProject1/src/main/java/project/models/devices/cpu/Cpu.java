package project.models.devices.cpu;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.devices.adapters.Socket;
import project.models.devices.general.Company;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Cpu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String series;
	private Integer cores;
	private Integer threads;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Company company;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Family family;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Line line;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Socket socket;
	private Integer lithography;
	private Integer tdp;
	private Integer baseClock;
	private Integer maxClockSingleCore;

}
