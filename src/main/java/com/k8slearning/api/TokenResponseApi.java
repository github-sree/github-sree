package com.k8slearning.api;

import java.util.Set;

import lombok.Data;

@Data
public class TokenResponseApi {

	private Set<String> privileges;
}
