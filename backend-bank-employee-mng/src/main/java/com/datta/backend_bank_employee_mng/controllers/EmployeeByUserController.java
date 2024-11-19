package com.datta.backend_bank_employee_mng.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datta.backend_bank_employee_mng.models.Employees;
import com.datta.backend_bank_employee_mng.response.MessageResponse;
import com.datta.backend_bank_employee_mng.services.EmployeeService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeByUserController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/current-employee")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Employees> getCurrentEmployeeDetails(){
		return new ResponseEntity<Employees>(employeeService.getCurrentEmployee(), HttpStatus.OK);
	}
	
	@GetMapping("/other-employee")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<List<Employees>> getOtherEmployees(){
		return new ResponseEntity<List<Employees>>(employeeService.getOtherEmployees(), HttpStatus.OK);
	}

	@PutMapping("/update-details")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<MessageResponse> updateDetails(@RequestBody Employees employee){
		MessageResponse message = employeeService.updateEmployeeByUser(employee);
		return new ResponseEntity<MessageResponse>(message, HttpStatus.OK);
	}

	@PatchMapping("/update-password")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> updatePassword(@RequestBody Map<String, String> password){
		return new ResponseEntity<MessageResponse>(employeeService.updatePasswordByUser(password), HttpStatus.OK);
	}
	
}
