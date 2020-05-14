package project.dto.user;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpForm {

	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String name;
	@NotEmpty
	@Size(min = 8)
	private String password;


//    @Pattern(regexp = "^id*$")
//    @NotBlank
//    private String vkId;
}

