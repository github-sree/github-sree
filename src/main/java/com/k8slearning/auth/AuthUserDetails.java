package com.k8slearning.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.k8slearning.model.Role;
import com.k8slearning.model.UserEntity;

public class AuthUserDetails implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private transient UserEntity user;

	public AuthUserDetails() {
		super();
	}

	public AuthUserDetails(UserEntity user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Role roles = user.getRole();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		roles.getPrivileges().forEach(auth -> authorities.add(new SimpleGrantedAuthority("ROLE_" + auth.getCode())));

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	public String getFirstName() {
		return user.getFirstName();
	}

	public String getLastName() {
		return user.getLastName();
	}

	public String getEmail() {
		return user.getEmail();
	}

	public String getRole() {
		return user.getRole().getRoleName();
	}

}
