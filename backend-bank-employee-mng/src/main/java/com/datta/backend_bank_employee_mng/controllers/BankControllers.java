package com.datta.backend_bank_employee_mng.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datta.backend_bank_employee_mng.models.Bank;
import com.datta.backend_bank_employee_mng.response.MessageResponse;
import com.datta.backend_bank_employee_mng.services.BankService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class BankControllers {
	
	@Autowired
	private BankService bankService;
	
	@GetMapping("/banks")
	public ResponseEntity<List<Bank>> fetchAllBanks(){
		return new ResponseEntity<List<Bank>>(bankService.getAllBanks(),HttpStatus.OK);
	}
	
	@PostMapping("/banks")
	public ResponseEntity<MessageResponse> addNewBank(@RequestBody Bank bank){
		MessageResponse message = bankService.addbank(bank);
		if (message.getMessage().equals("BranchName is already in use") || 
				message.getMessage().equals("BranchCode is already in use")) {
			return new ResponseEntity<MessageResponse>(message,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<MessageResponse>(message, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/banks/{id}")
	public ResponseEntity<MessageResponse> removeBank(@PathVariable Long id){
		return new ResponseEntity<MessageResponse> (bankService.removeBank(id), HttpStatus.NO_CONTENT);
	}
	
}
