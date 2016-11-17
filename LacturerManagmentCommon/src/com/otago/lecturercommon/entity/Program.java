package com.otago.lecturercommon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Program {

	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "smallint(3)")
	private int id;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "active", length = 1)
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
