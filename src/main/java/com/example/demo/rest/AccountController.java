package com.example.demo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.model.Account;
import com.example.demo.model.Login;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;
import com.example.demo.util.JwtTokenUtil;
@EnableWebMvc
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins="*")
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	private final JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	public AccountController(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil=jwtTokenUtil;
	}
	
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
	
	
	
	@DeleteMapping("/deleteAccount/{account_no}")
	public ResponseEntity<String> deleteByAccountNo(@PathVariable("account_no") long accountNo) {
		Optional<Account> accountOptional = accountRepository.findById(accountNo);
		if(accountOptional.isPresent()) {
			Account account = accountOptional.get();
			accountRepository.delete(account);
			return ResponseEntity.ok("Account deleted successfully.");
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable("userId") String userId, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		if(usernameFromToken.equals(userId))
		{
			return ResponseEntity.ok(accountRepository.findByLoginUserid(userId));
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@GetMapping("/admin/{adminUserId}/user/{userId}")
	public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable("adminUserId") String adminUserId,@PathVariable("userId") String userId, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken = jwtTokenUtil.getUsernameFromToken(token);
		if(usernameFromToken.equals(adminUserId))
		{
			return ResponseEntity.ok(accountRepository.findByLoginUserid(userId));
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{accountNo}")
	public ResponseEntity<Account> findAccountByAccountNo(@PathVariable("accountNo") long accountNo, @RequestHeader("Authorization") String header) {
		String token = header.replace("Bearer ","");
		String usernameFromToken =jwtTokenUtil.getUsernameFromToken(token);
		
		Optional<Account> accountOptional=accountRepository.findByAccountNo(accountNo);
		if(accountOptional.isPresent()) {
			Account account =accountOptional.get();
			Login login = account.getLogin();
			String userId =login.getUserid();
			System.out.println(userId);
			
			if(usernameFromToken.equals(userId)) {
				return ResponseEntity.ok(account);
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PutMapping("/{accountNo}")
    public ResponseEntity<Account> updateAccountPin(@PathVariable("accountNo") long accountNo, @RequestBody Account updatedAccount,@RequestHeader("Authorization") String header) {
		String token = header.replace("Bearer ","");
		String usernameFromToken =jwtTokenUtil.getUsernameFromToken(token);
        Optional<Account> existingAccountOptional = accountRepository.findByAccountNo(accountNo);
        if (existingAccountOptional.isPresent()) {
            Account existingAccount = existingAccountOptional.get();
            Login login = existingAccount.getLogin();
            String userId = login.getUserid();
            if(usernameFromToken.equals(userId))
            {
            	  System.out.println(updatedAccount.getBalance());
                  existingAccount.setBalance(updatedAccount.getBalance());
                  System.out.println(existingAccount);
                  Account updated = accountRepository.save(existingAccount);
                  return ResponseEntity.ok(updated);
            }
            else {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
          
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	@PutMapping("/accbalance/{accountNo}")
    public ResponseEntity<Account> updateAcocuntBalance(@PathVariable("accountNo") long accountNo, @RequestBody Account updatedAccount,@RequestHeader("Authorization") String header) {
		String token = header.replace("Bearer ","");
		String usernameFromToken =jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> existingAccountOptional = accountRepository.findByAccountNo(accountNo);
        if (existingAccountOptional.isPresent()) {
            Account existingAccount = existingAccountOptional.get();
            Login login = existingAccount.getLogin();
            String userId = login.getUserid();
            if(usernameFromToken.equals(userId))
            {
            	existingAccount.setBalance(updatedAccount.getBalance());       
                Account updated = accountRepository.save(existingAccount);
                return ResponseEntity.ok(updated);
            }
            else {
            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PutMapping("/admin/{adminUserId}/updateSuspend/{account_no}")
	public ResponseEntity<Account> updateAccountSuspend(@PathVariable("adminUserId") String adminUserId,@PathVariable("account_no") long accountNo, @RequestBody Account updatedAccount, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken =jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> existingAccountOptional = accountRepository.findByAccountNo(accountNo);
		if(existingAccountOptional.isPresent()) {
			Account existingAccount = existingAccountOptional.get();
//			long adminUserId1 = adminUserId;
//			long adminUserId2 = adminUserId;
			System.out.println("----------------------------------------");
			System.out.println(adminUserId);
			System.out.println(usernameFromToken);
			System.out.println("---------------------------------------");
			if(usernameFromToken.equals(adminUserId))
			{
				existingAccount.setSuspend(1);
				Account updated = accountRepository.save(existingAccount);
				return ResponseEntity.ok(updated);
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/admin/{adminUserId}/updateSuspendDeactive/{account_no}")
	public ResponseEntity<Account> updateAccountSuspendDeactive(@PathVariable("adminUserId") String adminUserId,@PathVariable("account_no") long accountNo, @RequestBody Account updatedAccount, @RequestHeader("Authorization") String header){
		String token = header.replace("Bearer ","");
		String usernameFromToken =jwtTokenUtil.getUsernameFromToken(token);
		Optional<Account> existingAccountOptional = accountRepository.findByAccountNo(accountNo);
		if(existingAccountOptional.isPresent()) {
			Account existingAccount = existingAccountOptional.get();
			if(usernameFromToken.equals(adminUserId))
			{
				existingAccount.setSuspend(0);
				Account updated = accountRepository.save(existingAccount);
				return ResponseEntity.ok(updated);	
			}
			else 
			{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
    

}