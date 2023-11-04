package com.k8slearning.api;

import com.k8slearning.utils.Constants;

import lombok.Data;

@Data
public abstract class ResponseApi implements Constants {
	protected String message;

	protected String status;
}
