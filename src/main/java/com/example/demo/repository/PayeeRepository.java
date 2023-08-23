package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Account;
import com.example.demo.model.Payee;

public interface PayeeRepository extends JpaRepository<Payee,Long> {
	@Query
	public List<Payee> findByAccountAccountNo(long accountNo);
	@Query
	public List<Payee> findByAccount(Account account);
}