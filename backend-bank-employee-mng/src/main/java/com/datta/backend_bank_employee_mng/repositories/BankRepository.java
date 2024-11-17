package com.datta.backend_bank_employee_mng.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datta.backend_bank_employee_mng.models.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}
