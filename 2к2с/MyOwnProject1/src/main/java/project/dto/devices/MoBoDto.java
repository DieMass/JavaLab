package project.dto.devices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.devices.motherboard.MotherBoard;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MoBoDto {

	private Long id;
	private String name;
	private String company;
	private String socket;
	private String chipset;
	private String formFactor;
	private String pcie;

	public static MoBoDto from(MotherBoard motherBoard) {
		return MoBoDto.builder()
				.id(motherBoard.getId())
				.name(motherBoard.getName())
				.company(motherBoard.getCompany().getName())
				.socket(motherBoard.getSocket().getName())
				.chipset(motherBoard.getChipset().getName())
				.formFactor(motherBoard.getFormFactor().getName())
//				.pcie(motherBoard.getPcie().getName())
				.build();
	}

	public static List<MoBoDto> from(List<MotherBoard> motherBoards) {
		return motherBoards.stream().map(MoBoDto::from).collect(Collectors.toList());
	}
}
