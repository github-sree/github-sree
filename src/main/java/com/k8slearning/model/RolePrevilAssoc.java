package com.k8slearning.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role_privilege_assoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePrevilAssoc {

	@Id
	private String rpId;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "roleId")
	private Role role;

	@ManyToOne
	@JoinColumn(name="privilege_id",referencedColumnName = "privilegeId")
	private Privilege privilege;

}
