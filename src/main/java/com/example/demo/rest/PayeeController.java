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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Login;
import com.example.demo.model.Payee;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PayeeRepository;
import com.example.demo.util.JwtTokenUtil;



@RestController
@RequestMapping("/payees")
@CrossOrigin(origins="*")
public class PayeeController {
	
	@Autowired
	private PayeeRepository payeeRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	private final JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	public PayeeController(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil=jwtTokenUtil;
	}
	
	@PostMapping
	public Payee createPayee(@RequestBody Payee payee)
	{
		payeeRepository.save(payee);
		return payee;
	}
	
	@GetMapping("/{accountNo}")
	public ResponseEntity<List<Payee>> getPayeeByAccountNo(@PathVariable("accountNo") long accountNo, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> accountOptional=accountRepository.findByAccountNo(accountNo);
		if(accountOptional.isPresent()) {
			Account account =accountOptional.get();
			Login login = account.getLogin();
			String userId =login.getUserid();
			if(usernameFromToken.equals(userId))
			{
				return ResponseEntity.ok(payeeRepository.findByAccountAccountNo(accountNo));
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
	public ResponseEntity<String> addPayee(@PathVariable("accountNo") long accountNo,@RequestBody Payee payee, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> accOptional = accountRepository.findByAccountNo(accountNo);
		if (accOptional.isPresent()) {
			Account acc = accOptional.get();
			Login login = acc.getLogin();
			String userId = login.getUserid();
			if(usernameFromToken.equals(userId))
			{
				payee.setAccountNoFromAccount(acc);
				payeeRepository.save(payee);
				return ResponseEntity.ok("New Payee added successfully");	
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			
		}else {
			return ResponseEntity.notFound().build();
			
		}
	}

}