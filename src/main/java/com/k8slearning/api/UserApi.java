package com.k8slearning.api;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Component
public class UserApi extends ResponseApi {
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

	@Override
	public void clear() {
		setUserId(null);
		setFirstName(null);
		setLastName(null);
		setUserName(null);
		setPassword(null);
		setEmail(null);
		setRole(null);
		setMessage(null);
		setStatus(null);
	}
}
