package com.k8slearning.api;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
public class AuthResponseApi extends ResponseApi {
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String role;
	private Set<String> authorities;
}
