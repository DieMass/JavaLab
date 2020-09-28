package die.mass.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PassportDto {

	private long number;
	private int age;
	private String date;
	private String name;
	private String surname;
}
