package com.k8slearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "k8_users")
@NamedEntityGraph(name = "graph.user-roles-privileges", attributeNodes = @NamedAttributeNode(value = "role", subgraph = "subgraph.privileges"), subgraphs = {
		@NamedSubgraph(name = "subgraph.privileges", attributeNodes = @NamedAttributeNode(value = "privileges")) })
public class UserEntity {
	@Id
	private String userId;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String userName;
	@Column(unique = true)
	private String email;
	@Column(name = "user_password", nullable = false)
	private String password;
	private boolean initialUser;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	@Column(name = "user_enabled")
	private boolean enabled;
	private boolean credentialsNonExpired;
	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "roleId")
	private Role role;

}
