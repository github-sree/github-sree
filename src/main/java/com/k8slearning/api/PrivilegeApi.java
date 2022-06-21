package com.k8slearning.api;

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
public class PrivilegeApi extends ResponseApi {
	private String privilegeId;
	private String name;
	private String code;

	@Override
	public void clear() {
		setPrivilegeId(null);
		setName(null);
		setCode(null);
		setMessage(null);
		setStatus(null);
	}
}
