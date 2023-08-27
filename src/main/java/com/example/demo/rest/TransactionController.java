package com.example.demo.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Login;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.util.JwtTokenUtil;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins="*")
public class TransactionController {
	
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	private final JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	public TransactionController(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil=jwtTokenUtil;
	}
	@GetMapping("/admin/{adminUserId}/user/{from_acc_no}")
//	Get all the transactions for a given account number 
	public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable("adminUserId") String adminUserId, @PathVariable("from_acc_no") long accountNo, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> accOptional = accountRepository.findByAccountNo(accountNo);
		if(accOptional.isPresent())
		{
			Account acc = accOptional.get();
			if(usernameFromToken.equals(adminUserId))
			{
				List<Transaction> transaction = transactionRepository.findLatestTransactions(accountNo);
				return ResponseEntity.ok(transaction);
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		else {
			return ResponseEntity.notFound().build();
		}

	}
	@GetMapping("/{from_acc_no}")
	public ResponseEntity<List<Transaction>> getLatestTransactions(@PathVariable("from_acc_no") long from_acc_no,@RequestParam int n, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> accOptional = accountRepository.findByAccountNo(from_acc_no);
		if(accOptional.isPresent())
		{
			Account acc = accOptional.get();
			Login login = acc.getLogin();
			String userId = login.getUserid();
			if(usernameFromToken.equals(userId))
			{
				List<Transaction> transaction = transactionRepository.findLatestTransactions(from_acc_no);
				return ResponseEntity.ok(transaction.subList(0,Math.min(n, transaction.size())));
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@GetMapping("/{from_acc_no}/totalcount")
	public ResponseEntity<Integer> getNoOfTotalTransactions(@PathVariable("from_acc_no") long from_acc_no,  @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> accOptional = accountRepository.findByAccountNo(from_acc_no);
		if(accOptional.isPresent())
		{
			Account acc = accOptional.get();
			Login login = acc.getLogin();
			String userId = login.getUserid();
			if(usernameFromToken.equals(userId))
			{
				List<Transaction> transaction = transactionRepository.findLatestTransactions(from_acc_no);
				return ResponseEntity.ok(transaction.size());
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		else {
			return ResponseEntity.notFound().build();
		}

	}
	
	@GetMapping("/{from_acc_no}/between-dates")
	public ResponseEntity<List<Transaction>> getTransactionBetweenDates(@PathVariable("from_acc_no") long from_acc_no, @RequestParam String from_date, @RequestParam String to_date,  @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> accOptional = accountRepository.findByAccountNo(from_acc_no);
		if(accOptional.isPresent())
		{
			Account acc = accOptional.get();
			Login login = acc.getLogin();
			String userId = login.getUserid();
			if(usernameFromToken.equals(userId))
			{
				List<Transaction> transaction =transactionRepository.findTransactionBetweenDates(from_acc_no, from_date, to_date);
				return ResponseEntity.ok(transaction);
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	

	
	@PostMapping("/{accountNo}")
	public ResponseEntity<String> addTransaction(@PathVariable("accountNo") long accountNo,@RequestBody Transaction transaction, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> accOptional = accountRepository.findByAccountNo(accountNo);
		if (accOptional.isPresent()) {
			Account acc = accOptional.get();
			Login login = acc.getLogin();
			String userId = login.getUserid();
			if(usernameFromToken.equals(userId))
			{
				transaction.setAccountNoFromAccount(acc);
				transactionRepository.save(transaction);
				return ResponseEntity.ok("New Transaction added successfully");
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			
		}else {
			return ResponseEntity.notFound().build();
			
		}
	}

}