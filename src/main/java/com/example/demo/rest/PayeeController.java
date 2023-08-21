package com.example.demo.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Login;
import com.example.demo.model.Payee;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PayeeRepository;

@RestController
@RequestMapping("/payees")
@CrossOrigin(origins="*")
public class PayeeController {
	
	@Autowired
	private PayeeRepository payeeRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	@PostMapping
	public Payee createPayee(@RequestBody Payee payee)
	{
		payeeRepository.save(payee);
		return payee;
	}
	
	@PostMapping("/{accountNo}")
	public ResponseEntity<String> addPayee(@PathVariable("accountNo") long accountNo,@RequestBody Payee payee){
		Optional<Account> accOptional = accountRepository.findByAccountNo(accountNo);
		if (accOptional.isPresent()) {
			Account acc = accOptional.get();
			payee.setAccountNoFromAccount(acc);
			payeeRepository.save(payee);
			return ResponseEntity.ok("New Payee added successfully");
		}else {
			return ResponseEntity.notFound().build();
			
		}
	}

}