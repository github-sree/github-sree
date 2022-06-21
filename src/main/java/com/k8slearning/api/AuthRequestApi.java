package com.k8slearning.api;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Component
@EqualsAndHashCode(callSuper = false)
public class AuthRequestApi extends ResponseApi {
	private String userName;

	private String passWord;

	@Override
	public void clear() {
		setUserName(null);
		setPassWord(null);
		setMessage(null);
		setStatus(null);
	}

}
