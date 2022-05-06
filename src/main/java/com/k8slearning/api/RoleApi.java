package com.k8slearning.api;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.k8slearning.model.Privilege;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class RoleApi {
	private String roleId;
	@NotNull(message = "role name is required")
	private String roleName;
	private boolean active = true;
	private Set<String> privilegeNames;
	private Set<PrivilegeApi> privileges;

}
