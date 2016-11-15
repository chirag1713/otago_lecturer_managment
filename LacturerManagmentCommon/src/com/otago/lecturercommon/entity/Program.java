package com.otago.lecturercommon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Program {

	@Id
	@Column(name = "id", columnDefinition = "smallint(3)")
	private int id;

	@Column(name = "name", length = 30)
	private String name;

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
