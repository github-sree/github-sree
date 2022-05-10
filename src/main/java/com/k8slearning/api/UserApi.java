package com.k8slearning.api;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserApi {
	private String userId;
	private String firstName;
	@NotNull(message = "Last name is required")
	private String lastName;
	@NotNull(message = "User-name is required")
	private String userName;
	@NotNull(message = "Email is required")
	private String email;
	@NotNull(message = "Password is required")
	private String password;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean enabled = true;
	private boolean credentialsNonExpired = true;
	private RoleApi role;

	public UserApi(@NotNull(message = "User-name is required") String userName) {
		super();
		this.userName = userName;
	}

}
