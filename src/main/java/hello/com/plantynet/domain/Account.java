package hello.com.plantynet.domain;

import lombok.Data;

@Data
public class Account {

	private Long id;
	private String username;
	private String password;
	private String role;
}
