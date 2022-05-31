package com.k8slearning.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.k8slearning.api.PrivilegeApi;
import com.k8slearning.model.Privilege;
import com.k8slearning.utils.CustomException;

public interface PrivilegeService {
	public PrivilegeApi createPrivileges(PrivilegeApi privilegeApi);

	Page<Privilege> retrievePrivileges(Pageable pageable);

	public PrivilegeApi updatePrivilege(String privilegeId, PrivilegeApi privilegeApi);

	public void deletePrivilege(String privilegeId) throws CustomException;
}
