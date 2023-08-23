package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
	@Query("SELECT t FROM Transaction t where t.account.accountNo = :fromAccNo ORDER BY t.date DESC")
	List<Transaction> findLatestTransactions(long fromAccNo);
	
	@Query("SELECT t FROM Transaction t where t.account.accountNo= :fromAccNo AND (t.date>= :fromDate AND t.date<= :toDate) ORDER BY t.date DESC")
	List<Transaction> findTransactionBetweenDates(long fromAccNo,String fromDate,String toDate);
	
}