package com.k8slearning.api;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class TokenResponseApi extends ResponseApi {

	private Set<String> privileges;

	@Override
	public void clear() {
		setPrivileges(null);
		setMessage(null);
		setStatus(null);
	}
}
