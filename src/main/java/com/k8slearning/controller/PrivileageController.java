package com.k8slearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k8slearning.api.PagingResponseApi;
import com.k8slearning.api.PrivilegeApi;
import com.k8slearning.model.Privilege;
import com.k8slearning.service.PrivilegeService;
import com.k8slearning.utils.ConstantsUtil;
import com.k8slearning.utils.CustomException;
import com.k8slearning.utils.K8sUtils;

@RestController
@RequestMapping("/v1/auth")
public class PrivileageController {

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private K8sUtils utils;

	@PostMapping("/privilege")
	@PreAuthorize(ConstantsUtil.Privileges.PRIVILEGE_CREATE)
	public ResponseEntity<PrivilegeApi> createPrivileges(@RequestBody PrivilegeApi privilegeApi) {
		return new ResponseEntity<>(privilegeService.createPrivileges(privilegeApi), HttpStatus.OK);
	}

	@GetMapping("/privilege")
	@PreAuthorize(ConstantsUtil.Privileges.PRIVILEGE_READ)
	public ResponseEntity<PagingResponseApi<PrivilegeApi>> retrievePrivileges(final Pageable pageable) {
		Page<Privilege> privilage = privilegeService.retrievePrivileges(pageable);
		PagingResponseApi<PrivilegeApi> contents = new PagingResponseApi<>();
		contents.setTotalCount(privilage.getTotalElements());
		contents.setPageNo(privilage.getNumber());
		contents.setTotalPages(privilage.getTotalPages());
		contents.setPageSize(privilage.getNumberOfElements());
		contents.setRows(utils.mapStream(privilage.get(), PrivilegeApi.class));
		return new ResponseEntity<>(contents, HttpStatus.OK);
	}

	@PutMapping("/privilege/{privilegeId}")
	@PreAuthorize(ConstantsUtil.Privileges.PRIVILEGE_UPDATE)
	public ResponseEntity<PrivilegeApi> updatePrivilege(@PathVariable String privilegeId,
			@RequestBody PrivilegeApi privilegeApi) {
		return new ResponseEntity<>(privilegeService.updatePrivilege(privilegeId, privilegeApi), HttpStatus.OK);
	}

	@DeleteMapping("/privilege/{privilegeId}")
	@PreAuthorize(ConstantsUtil.Privileges.PRIVILEGE_DELETE)
	public void deletePrivilege(@PathVariable String privilegeId) throws CustomException {
		privilegeService.deletePrivilege(privilegeId);
	}

}
