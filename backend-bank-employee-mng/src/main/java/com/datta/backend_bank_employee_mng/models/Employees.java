package com.datta.backend_bank_employee_mng.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
//import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Employees {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String empId;
	private String username;
	private String name;
	private String email;
	private String job;
	private String password;
	private String mobileNo;
	private String address;
	private boolean status;
	@ManyToMany
	@JoinTable(
	    name = "users_roles",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
	
	@ManyToOne
//	@JsonBackReference
	private Bank bank;
	
	public Employees() {}

	public Employees(String empId, String name, String email, String job, String password, Bank bank) {
		super();
		this.empId = empId;
		this.name = name;
		this.email = email;
		this.job = job;
		this.password = password;
		this.bank = bank;
	}

	public Long getId() {
		return id;
	}

	public String getEmpId() {
		return empId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getJob() {
		return job;
	}

	public String getPassword() {
		return password;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public boolean isStatus() {
		return status;
	}

	public Bank getBank() {
		return bank;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Employees [id=" + id + ", empId=" + empId + ", name=" + name + ", email=" + email + ", job=" + job
				+ ", password=" + password + ", mobileNo=" + mobileNo + ", address=" + address + ", status=" + status
				+ ", bank=" + bank + "]";
	}
	
}
