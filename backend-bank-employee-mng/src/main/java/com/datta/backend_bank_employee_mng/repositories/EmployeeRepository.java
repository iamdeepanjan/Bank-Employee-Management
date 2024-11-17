package com.datta.backend_bank_employee_mng.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datta.backend_bank_employee_mng.models.Employees;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long>{

	boolean existsByEmail(String email);
	
	boolean existsByEmpId(String empId);
}
