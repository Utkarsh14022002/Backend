package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;

import java.util.Optional;

import com.example.demo.model.Login;
@EnableWebMvc
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins="*")
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@PostMapping
	public Account createAccount(@RequestBody Account account)
	{
		accountRepository.save(account);
		return account;
	}
	
	@PostMapping("/{userId}") 
	public ResponseEntity<String> addAccountForUserId(@PathVariable String userId,@RequestBody Account account){
		Optional<Login> userOptional = loginRepository.findByUserid(userId);
		if (userOptional.isPresent()) {
			Login user = userOptional.get();
			account.setUserIdFromLogin(user);
			accountRepository.save(account);
			return ResponseEntity.ok("Account added successfully");
		}else {
			return ResponseEntity.notFound().build();
			
		}
	}

}