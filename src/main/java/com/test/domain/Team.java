package com.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.test.domain.base.BaseEntity;

@Entity
@Table(name = "Team")
public class Team extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "Name", nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
