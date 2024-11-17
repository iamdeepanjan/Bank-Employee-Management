package com.datta.backend_bank_employee_mng.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.datta.backend_bank_employee_mng.models.ERole;
import com.datta.backend_bank_employee_mng.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);
}
