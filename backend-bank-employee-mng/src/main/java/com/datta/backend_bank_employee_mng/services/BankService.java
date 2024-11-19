package com.datta.backend_bank_employee_mng.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datta.backend_bank_employee_mng.exceptions.ResourceNotFoundException;
import com.datta.backend_bank_employee_mng.models.Bank;
import com.datta.backend_bank_employee_mng.repositories.BankRepository;
import com.datta.backend_bank_employee_mng.response.MessageResponse;

@Service
public class BankService {

	@Autowired
	private BankRepository bankRepository;
	
	public List<Bank> getAllBanks(){
		return bankRepository.findAll();
	}
	
	public MessageResponse addbank(Bank bank) {
		if(bankRepository.existsByBranchName(bank.getBranchName())) {
			return new MessageResponse("BranchName is already in use");
		}
		if(bankRepository.existsByBranchCode(bank.getBranchCode())) {
			return new MessageResponse("BranchCode is already in use");
		}
		bankRepository.save(bank);
		return new MessageResponse("Bank is added successfully");
	}
	
	public MessageResponse removeBank(Long id) {
		Bank bank = bankRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Bank is not exist for this id "+ id));
		bankRepository.delete(bank);
		return new MessageResponse("Bank removed successfully");
	}
}
