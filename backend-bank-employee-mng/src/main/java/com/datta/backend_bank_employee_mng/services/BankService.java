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
	
	public Bank addbank(Bank bank) {
		return bankRepository.save(bank);
	}
	
	public MessageResponse removeBank(Long id) {
		Bank bank = bankRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Bank is not exist for this id "+ id));
		bankRepository.delete(bank);
		return new MessageResponse("Bank removed successfully");
	}
}
