package com.example.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Payee;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins="*")
public class TransactionController {
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("/{from_acc_no}")
	public List<Transaction> getLatestTransactions(@PathVariable("from_acc_no") long from_acc_no,@RequestParam int n){
		List<Transaction> transaction = transactionRepository.findLatestTransactions(from_acc_no);
		return transaction.subList(0,Math.min(n, transaction.size()));

	}
	
	@GetMapping("/{from_acc_no}/between-dates")
	public List<Transaction> getTransactionBetweenDates(
			@PathVariable("from_acc_no") long from_acc_no,
			@RequestParam String from_date,
			@RequestParam String to_date){
		List<Transaction> transactions =transactionRepository.findTransactionBetweenDates(from_acc_no, from_date, to_date);
		return transactions;
	}
	
//	@PostMapping
//	public Transaction createLogin(@RequestBody Transaction transaction)
//	{
//		transactionRepository.save(transaction);
//		return transaction;
//	}
	
	@PostMapping("/{accountNo}")
	public ResponseEntity<String> addTransaction(@PathVariable("accountNo") long accountNo,@RequestBody Transaction transaction){
		Optional<Account> accOptional = accountRepository.findByAccountNo(accountNo);
		if (accOptional.isPresent()) {
			Account acc = accOptional.get();
			transaction.setAccountNoFromAccount(acc);
			transactionRepository.save(transaction);
			return ResponseEntity.ok("New Transaction added successfully");
		}else {
			return ResponseEntity.notFound().build();
			
		}
	}

}