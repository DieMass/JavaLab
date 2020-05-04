package project.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.models.user.User;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {


	private Long id;
	private String name;
	private String email;

	public static UserDto from(User user) {
		return UserDto.builder()
				.email(user.getEmail())
				.name(user.getName())
				.id(user.getId())
				.build();
	}

	public static List<UserDto> from(List<User> users) {
		return users.stream()
				.map(UserDto::from)
				.collect(Collectors.toList());
	}
}
