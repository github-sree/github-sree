package com.k8slearning.api;

import com.k8slearning.utils.ConstantsUtil;

import lombok.Data;

@Data
public abstract class ResponseApi implements ConstantsUtil {
	protected String message;

	protected String status;
}
