package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;
@EnableWebMvc
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins="*")
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	@PostMapping
	public Account createAccount(@RequestBody Account account)
	{
		accountRepository.save(account);
		return account;
	}

}