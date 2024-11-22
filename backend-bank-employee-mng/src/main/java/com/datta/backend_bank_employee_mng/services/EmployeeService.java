package com.datta.backend_bank_employee_mng.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.datta.backend_bank_employee_mng.exceptions.ResourceNotFoundException;
import com.datta.backend_bank_employee_mng.models.ERole;
import com.datta.backend_bank_employee_mng.models.Employees;
import com.datta.backend_bank_employee_mng.models.Role;
import com.datta.backend_bank_employee_mng.repositories.EmployeeRepository;
import com.datta.backend_bank_employee_mng.repositories.RoleRepository;
import com.datta.backend_bank_employee_mng.response.MessageResponse;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
//	By Admin service
	public List<Employees> getAllEmployees() {
		List<Employees> users = employeeRepository.findAll();
		List<Employees> filteredUsers = users.stream().filter(user -> user.getRoles().stream()
				.allMatch(role -> role.getName().equals(ERole.valueOf("ROLE_USER")))).collect(Collectors.toList());
		return filteredUsers;
	}

	public Employees getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee is not exist for this id"+ id));
	}
	
	public MessageResponse addEmployeeByAdmin(Employees employees) {
		if(employeeRepository.existsByEmail(employees.getEmail())) {
			return new MessageResponse("Email is already in use");
		}
		if(employeeRepository.existsByEmpId(employees.getEmpId())) {
			return new MessageResponse("Employee id is already in use");
		}
		Employees newEmployee = new Employees(employees.getEmpId(), employees.getName(), employees.getEmail(), 
				employees.getJob(), encoder.encode(employees.getPassword()), employees.getBank());
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(()-> new RuntimeException("Role is not found"));
		roles.add(userRole);
		newEmployee.setUsername(employees.getEmail());
		newEmployee.setRoles(roles);
		newEmployee.setStatus(true);
		
		employeeRepository.save(newEmployee);
		return new MessageResponse("User created successfully");
	}
	
	public MessageResponse updateEmployeeByAdmin(Long id, Employees employeesDetails) {
		Employees employee = getEmployeeById(id);
		if(employee.getEmail().equals(employeesDetails.getEmail())) {
			employee.setEmpId(employeesDetails.getEmpId());
			employee.setEmail(employeesDetails.getEmail());
			employee.setName(employeesDetails.getName());
			employee.setJob(employeesDetails.getJob());
			employee.setBank(employeesDetails.getBank());
			employee.setStatus(true);
			employeeRepository.save(employee);
			return new MessageResponse("User updated successfully");
		}
		else {
			if(employeeRepository.existsByEmail(employeesDetails.getEmail())) {
				return new MessageResponse("Email is already in use");
			}
			if(employeeRepository.existsByEmpId(employeesDetails.getEmpId())) {
				return new MessageResponse("Employee id is already in use");
			}
			employee.setEmpId(employeesDetails.getEmpId());
			employee.setEmail(employeesDetails.getEmail());
			employee.setName(employeesDetails.getName());
			employee.setJob(employeesDetails.getJob());
			employee.setBank(employeesDetails.getBank());
			employee.setStatus(true);
			employeeRepository.save(employee);
			return new MessageResponse("User updated successfully");
		}
	}
	
	public MessageResponse approveEmployeeByAdmin(Long id) {
		Employees employee = getEmployeeById(id);
		employee.setStatus(true);
		employeeRepository.save(employee);
		return new MessageResponse("User approved successfully");
	}
	
	public MessageResponse removeEmployeeByAdmin(Long id) {
		Employees employee = getEmployeeById(id);
		employeeRepository.delete(employee);
		return new MessageResponse("User removed successfully");
	}
	
	
//	By User service
	public Employees getCurrentEmployee() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		UserDetailsImpl loggedUser = (UserDetailsImpl) authentication.getPrincipal();
//		return getEmployeeById(loggedUser.getId());
		Jwt jwt = (Jwt) authentication.getPrincipal();
	    String userId = jwt.getClaimAsString("userId");
	    return getEmployeeById(Long.parseLong(userId));
	}
	
	public List<Employees> getOtherEmployees() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt) authentication.getPrincipal();
	    String userId = jwt.getClaimAsString("userId");
		Employees employee = getEmployeeById(Long.parseLong(userId));
		List<Employees> employees = getAllEmployees();
		List<Employees> otherEmployees = employees.stream()
				.filter(user -> !user.getId().equals(employee.getId()))
				.filter(user -> user.getBank().getId().equals(employee.getBank().getId()))
				.collect(Collectors.toList());
		return otherEmployees;
	}
	
	public MessageResponse updateEmployeeByUser(Employees employeesDetails) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt) authentication.getPrincipal();
	    String userId = jwt.getClaimAsString("userId");
		Employees employee = getEmployeeById(Long.parseLong(userId));
		employee.setName(employeesDetails.getName());
		employee.setMobileNo(employeesDetails.getMobileNo());
		employee.setAddress(employeesDetails.getAddress());
		employee.setStatus(false);
		employeeRepository.save(employee);
		return new MessageResponse("Your details updated successfully");
	}
	
	public MessageResponse updatePasswordByUser(Map<String, String> password) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt) authentication.getPrincipal();
	    String userId = jwt.getClaimAsString("userId");
		Employees employee = getEmployeeById(Long.parseLong(userId));
		String currentPassword = password.get("currentPassword");
		String newPassword = password.get("newPassword");
		if(!encoder.matches(currentPassword, employee.getPassword())) {
			return new MessageResponse("Current password is incorrect");
		}
		employee.setPassword(encoder.encode(newPassword));
		employee.setStatus(false);
		employeeRepository.save(employee);
		return new MessageResponse("Your password updated successfully");
	}
}
