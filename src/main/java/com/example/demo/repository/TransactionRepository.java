package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
	@Query("SELECT t FROM Transaction t where t.account.accountNo = :from_acc_no ORDER BY t.date DESC")
	List<Transaction> findLatestTransactions(long from_acc_no);
}