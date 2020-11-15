package die.mass.rabbitmq.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PassportDto {

	private int series;
	private long number;
	private int birthYear;
	private String date;
	private String name;
	private String surname;
	private String patronymic;
	private String birthCountry;
	private Boolean isConfirmed;
	private String service;

}
