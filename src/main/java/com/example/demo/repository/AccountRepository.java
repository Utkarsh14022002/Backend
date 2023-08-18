package com.example.demo.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Account;
import com.example.demo.model.Login;

public interface AccountRepository extends JpaRepository<Account,Long> {
	
	@Query
	public void createNewAccount(@RequestBody Account account);

	
}