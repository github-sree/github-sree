package com.k8slearning.service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.k8slearning.api.PrivilegeApi;
import com.k8slearning.model.Privilege;
import com.k8slearning.repository.PrivilegeRepository;
import com.k8slearning.utils.CustomException;

@Service
public class PrivilegeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrivilegeService.class);

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Autowired
	ModelMapper modelMap;

	PrivilegeApi privilegeResponse = null;

	public PrivilegeApi createPrivileges(PrivilegeApi privilegeApi) {
		PrivilegeApi response = null;
		try {
			Privilege privil = modelMap.map(privilegeApi, Privilege.class);
			privil.setPrivilegeId(UUID.randomUUID().toString());
			privilegeRepository.save(privil);
			response = modelMap.map(privil, PrivilegeApi.class);
			LOGGER.info("privilege saved with id::{}", privil.getPrivilegeId());
		} catch (Exception e) {
			LOGGER.error("exception in privil service {}", e.getMessage());
		}
		return response;
	}

	public Page<Privilege> retrievePrivileges(Pageable pageable) {
		return privilegeRepository.findAll(pageable);
	}

	public PrivilegeApi updatePrivilege(String privilegeId, PrivilegeApi privilegeApi) {
		privilegeResponse = null;
		try {
			Optional<Privilege> privilege = privilegeRepository.findById(privilegeId);
			privilege.ifPresent(pDto -> {
				privilegeResponse = new PrivilegeApi();
				modelMap.map(privilegeApi, pDto);
				pDto.setPrivilegeId(privilegeId);
				privilegeRepository.save(pDto);
				privilegeResponse = modelMap.map(pDto, PrivilegeApi.class);
				LOGGER.info("privilege updated with id::{}", pDto.getPrivilegeId());
			});
		} catch (Exception e) {
			LOGGER.error("exception in privil update {}", e.getMessage());
		}
		return privilegeResponse;
	}

	public void deletePrivilege(String privilegeId) throws CustomException {
		try {
			privilegeRepository.findById(privilegeId).ifPresent(privil -> {
				privilegeRepository.deleteById(privilegeId);
				LOGGER.info("privilege deleted {}", privilegeId);
			});
		} catch (Exception e) {
			LOGGER.error("exception in privil delete {}", e.getMessage());
			throw new CustomException("Unable to delete privilegeId " + privilegeId);
		}
	}

}
