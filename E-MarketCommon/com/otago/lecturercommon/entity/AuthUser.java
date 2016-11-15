package com.otago.lecturercommon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuthUser {
	@Id
	@Column(name = "userid", columnDefinition = "INT(11)")
	private int userId;

	@Column(name = "password", length = 42)
	private String password;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
