package com.k8slearning.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "k8_roles")
public class Role {
	@Id
	private String roleId;
	private String roleName;
	private boolean active;
	private boolean initialRole;
	@OneToMany(targetEntity = Privilege.class, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Privilege> privileges;
}
