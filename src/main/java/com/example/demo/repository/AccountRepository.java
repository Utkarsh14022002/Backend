package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
	@Query
	public Optional<Account> findByAccountNo(long accountNo);
	
}