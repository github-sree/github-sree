package com.k8slearning.api;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TokenResponseApi extends ResponseApi {

	private Set<String> privileges;
}
