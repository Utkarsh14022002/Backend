package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Account;
import com.example.demo.model.Login;

public interface AccountRepository extends JpaRepository<Account,Long> {
	@Query
	public Optional<Account> findByAccountNo(long accountNo);
	@Query
	public List<Account> findByLoginUserid(String userId);
	@Query
	public List<Account> findByLogin(Login login);
	
}