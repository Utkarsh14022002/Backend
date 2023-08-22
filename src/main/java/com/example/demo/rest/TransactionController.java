package com.example.demo.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Payee;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;
	
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