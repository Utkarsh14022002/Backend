package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;

import java.util.HashMap;
import java.util.Map;
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
	
	@GetMapping("/{accountNo}")
	public ResponseEntity<Optional<Account>> findAccountByAccountNo(@PathVariable("accountNo") long accountNo) {

		Optional<Account> inOptional=accountRepository.findByAccountNo(accountNo);
		System.out.println(inOptional);
		return ResponseEntity.ok(inOptional);
	}
	
	
//	@PutMapping("/{userId}")
//    public ResponseEntity<Login> updateLogin(@PathVariable("userId") String userId, @RequestBody Login updatedLogin) {
//        Optional<Login> existingLoginOptional = loginRepository.findByUserId(userId);
//        if (existingLoginOptional.isPresent()) {
//            Login existingLogin = existingLoginOptional.get();
//            existingLogin.setPassword(updatedLogin.getPassword());
//            existingLogin.setAdmin(updatedLogin.getAdmin());
//            existingLogin.setEmail(updatedLogin.getEmail());
//
//            Login updated = loginRepository.save(existingLogin);
//            return new ResponseEntity<>(updated, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
	
    

}