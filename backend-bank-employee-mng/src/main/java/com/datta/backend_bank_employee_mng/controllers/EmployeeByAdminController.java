package com.datta.backend_bank_employee_mng.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class EmployeeByAdminController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Employees>> fetchAllEmployees(){
		return new ResponseEntity<List<Employees>>(employeeService.getAllEmployees(), HttpStatus.OK);
	}
	
	@GetMapping("/employees/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Employees> fetchEmployeeById(@PathVariable Long id){
		return new ResponseEntity<Employees>(employeeService.getEmployeeById(id), HttpStatus.OK);
	}
	
	@PostMapping("/employees")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> addEmployee(@RequestBody Employees employee){
		MessageResponse message = employeeService.addEmployeeByAdmin(employee);
		if (message.getMessage().equals("Email is already in use") || 
				message.getMessage().equals("Employee id is already in use")) {
			return new ResponseEntity<MessageResponse>(message,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MessageResponse>(message, HttpStatus.CREATED);
	}
	
	@PutMapping("/employees/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> updateEmployee(@PathVariable Long id, @RequestBody Employees employee){
		MessageResponse message = employeeService.updateEmployeeByAdmin(id,employee);
		if (message.getMessage().equals("Email is already in use") || 
				message.getMessage().equals("Employee id is already in use")) {
			return new ResponseEntity<MessageResponse>(message,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MessageResponse>(message, HttpStatus.OK);
	}

	@PatchMapping("/employees/{id}/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> approveEmployee(@PathVariable Long id){
		return new ResponseEntity<MessageResponse>(employeeService.approveEmployeeByAdmin(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/employees/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> removeEmployee(@PathVariable Long id){
		return new ResponseEntity<MessageResponse> (employeeService.removeEmployeeByAdmin(id), HttpStatus.NO_CONTENT);
	}
	
}
