package com.app.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Data
@Table(name = "userInfo_tbl")
public class UserInfo {

	@Id
	@GeneratedValue
	private Integer Id;
	private String username;
	private String email;
	private String pass;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles_tbl",joinColumns = @JoinColumn(referencedColumnName = "id"))
	private Set<String> roles;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	
}











