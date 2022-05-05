package com.k8slearning.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

@Service
public class PrivilegeService {

	Logger logger = LoggerFactory.getLogger(PrivilegeService.class);

	@Autowired
	PrivilegeRepository privilegeRepository;

	@PersistenceContext
	EntityManager em;

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
		} catch (Exception e) {
			logger.error("exception in privil service {}", e.getMessage());
		}
		return response;
	}

	public Page<Privilege> retrievePrivileges(Pageable pageable) {
		return privilegeRepository.findAll(pageable);
	}

	public PrivilegeApi updatePrivilege(String privilegeId, PrivilegeApi privilegeApi) {
		privilegeResponse = null;
		try {
			logger.info("privil api::{}", privilegeApi);
			Optional<Privilege> privilege = privilegeRepository.findById(privilegeId);
			logger.info("is privilege present? {}", privilege.isPresent());
			privilege.ifPresent(pDto -> {
				privilegeResponse = new PrivilegeApi();
				modelMap.map(privilegeApi, pDto);
				pDto.setPrivilegeId(privilegeId);
				logger.info("privil before{}", pDto);
				privilegeRepository.save(pDto);
				logger.info("privil after{}", pDto);
				privilegeResponse = modelMap.map(pDto, PrivilegeApi.class);
			});
		} catch (Exception e) {
			logger.error("exception in privil update {}", e.getMessage());
		}
		return privilegeResponse;
	}

}
