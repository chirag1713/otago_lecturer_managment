/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otago.lecturercommon.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Admin
 */
@Entity
public class Status {

	public static final int SUCCESS = 1;
	public static final int PENDDING = 2;
	public static final int FAILED = 3;
	public static final int ACTIVE = 4;
	public static final int INACTIVE = 5;
	public static final int UPLOADED = 6;
	public static final int DELETED = 7;
	public static final int INITIATED = 8;

	@Id
	@Column(name = "id", columnDefinition = "smallint(3)")
	private int id;

	@Column(name = "name", length = 30)
	private String name;

	public Status() {
	}

	public Status(int id) {
		this.id = id;
	}

	public Status(String name) {
		this.name = name;
	}

	public Status(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Status{" + "id=" + id + ", name=" + name + '}';
	}

	public Map<String, Object> toMap() {
		HashMap<String, Object> dataMap = new HashMap<String, Object>(2);
		dataMap.put("id", this.id);
		dataMap.put("name", this.name);
		return dataMap;
	}

}
