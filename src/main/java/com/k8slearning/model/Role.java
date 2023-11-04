package com.k8slearning.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
