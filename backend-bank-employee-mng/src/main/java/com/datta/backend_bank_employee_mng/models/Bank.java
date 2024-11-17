package com.datta.backend_bank_employee_mng.models;

import java.util.List;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "banks")
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String bankName;
	private String branchName;
	private String branchCode;
	
	@OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
//	@JsonManagedReference
	private List<Employees> employees;
	
	public Bank() {}

	public Bank(long id, String bankName, String branchName, String branchCode) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.branchName = branchName;
		this.branchCode = branchCode;
	}

	public Long getId() {
		return id;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Override
	public String toString() {
		return "Bank [id=" + id + ", bankName=" + bankName + ", branchName=" + branchName + ", branchCode=" + branchCode
				+ "]";
	}
	
}
