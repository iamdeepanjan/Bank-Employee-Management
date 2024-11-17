package com.datta.backend_bank_employee_mng.models;

import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@Enumerated(EnumType.STRING)
	private ERole name;
	
	public Role() {}

	public Role(Long id, ERole name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public ERole getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(ERole name) {
		this.name = name;
	}
	
}
