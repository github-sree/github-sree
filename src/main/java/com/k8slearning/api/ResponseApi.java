package com.k8slearning.api;

import lombok.Data;

@Data
public abstract class ResponseApi {
	protected String message;

	protected String status;
}
