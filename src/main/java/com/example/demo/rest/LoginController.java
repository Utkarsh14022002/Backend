package com.example.demo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

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

import com.example.demo.model.Login;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;
@EnableWebMvc
@RestController
@RequestMapping("/logins")
@CrossOrigin(origins="*")
public class LoginController {
    private LoginRepository loginRepository;
    private EmailService emailService; // Inject your EmailService here
    @Autowired
	private AccountRepository accountRepository;

    public LoginController(LoginRepository loginRepository, EmailService emailService) {
        this.loginRepository = loginRepository;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Login> createLogin(@RequestBody Login login) {
        Optional<Login> existingLoginOptional = loginRepository.findByUserid(login.getUserid());

        if (existingLoginOptional.isPresent()) {
            return ResponseEntity.badRequest().body(existingLoginOptional.get());
        }

        Login i = loginRepository.save(login);
        return new ResponseEntity<Login>(i, HttpStatus.CREATED);
    }
    String perotp;
    @PostMapping("/{emailid}")
    public ResponseEntity<Map<String, String>> validateEmailAndSendOTP(@RequestBody Map<String, String> request) {
        String email = request.get("emailid");
   

        Optional<Login> existingLoginOptional = loginRepository.findByEmailid(email);

        if (existingLoginOptional.isPresent()) {
            // Generate OTP
            String otp = generateOTP();
            perotp=otp;

            // Send OTP via email
            emailService.sendEmail(email, "Your OTP", "Your OTP: " + otp);

            // Return success response
            Map<String, String> response = new HashMap<>();
            response.put("message", "OTP sent successfully");
            return ResponseEntity.ok(response);
        } else {
            // Return failure response
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email not found");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<Login>> validateUserid(@PathVariable("userId") String userId) {
        Optional<Login> user = loginRepository.findByUserid(userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOTP(@RequestBody Map<String, String> request) {
        String email = request.get("emailid");
        String otp = request.get("otp");

        // Validate OTP and get user ID from backend logic
        boolean otpValid = otp.equals(perotp);
        if (!otpValid) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Incorrect OTP");
            return ResponseEntity.badRequest().body(response);
        }

        // Get user ID from the database based on the email
        Optional<Login> loginOptional = loginRepository.findByEmailid(email);
        if (loginOptional.isPresent()) {
            String userId = loginOptional.get().getUserid();

            Map<String, String> response = new HashMap<>();
            response.put("message", "OTP verified successfully");
            response.put("userId", userId); // Include the user ID in the response
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    
    @PostMapping("/verify-otpp")
    public ResponseEntity<Map<String, String>> verifyOTPP(@RequestBody Map<String, String> request) {
        String email = request.get("emailid");
        String otp = request.get("otp");

        // Validate OTP and get user ID from backend logic
        boolean otpValid = otp.equals(perotp);
        if (!otpValid) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Incorrect OTP");
            return ResponseEntity.badRequest().body(response);
        }

        // Get user ID from the database based on the email
        Optional<Login> loginOptional = loginRepository.findByEmailid(email);
        if (loginOptional.isPresent()) {
            String password = loginOptional.get().getPassword();

            Map<String, String> response = new HashMap<>();
            response.put("message", "OTP verified successfully");
            response.put("password", password); // Include the user ID in the response
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email not found");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/updatePassword/{userid}")
    public ResponseEntity<String> updatePassword(@PathVariable("userid") String userId, @RequestBody Map<String,String> request){
    	String newPassword = request.get("newPassword");
    	Optional <Login>optionalUser = loginRepository.findByUserid(userId);
    	if(optionalUser.isPresent()) {
    		Login user = optionalUser.get();
    		user.setPassword(newPassword);
    		loginRepository.save(user);
    		return ResponseEntity.ok("success");
    	}else {
    		return ResponseEntity.notFound().build();
    	}
    	
    }
    
    
    @PutMapping("/{email}")
    public ResponseEntity<String> updateNewPassword(@PathVariable("email") String emailid, @RequestBody Map<String,String> request){
    	String newPassword = request.get("newPassword");
    	Optional <Login>optionalUser = loginRepository.findByEmailid(emailid);
    	if(optionalUser.isPresent()) {
    		Login user = optionalUser.get();
    		user.setPassword(newPassword);
    		loginRepository.save(user);
    		return ResponseEntity.ok("success");
    	}else {
    		return ResponseEntity.notFound().build();
    	}
    	
    }
    
//    @Transactional
//    @PutMapping("/forgetuserid/{email}")
//    public ResponseEntity<String> updateNewUserID(@PathVariable("email") String emailid, @RequestBody Map<String,String> request){
//    	String newUser = request.get("newUser");
//    	Optional <Login>optionalUser = loginRepository.findByEmailid(emailid);
//    	if(optionalUser.isPresent()) {
//    		Login user = optionalUser.get();
//    		List<Account> accountWithOldUser = accountRepository.findByLogin(user);
//    		System.out.println(user);
//    		user.setUserid(newUser);
//    		System.out.println(user);
//    		loginRepository.save(user);
//    		
//    		
//    		for (Account account : accountWithOldUser) {
//    			System.out.println(account);
//    			account.setUserIdFromLogin(user);
//    			accountRepository.save(account);
//    			
//    		}
//    		return ResponseEntity.ok("success");
//    	}else {
//    		return ResponseEntity.notFound().build();
//    	}
//    	
//    }
    
    @GetMapping("/users")
    public List<Login> validateUserId(){
    	return loginRepository.findAll();
    }
    
    @GetMapping("/{userid}")
    public ResponseEntity<Login> validateUserId(@PathVariable("userid") String userid) {
        Optional<Login> existingLoginOptional = loginRepository.findByUserid(userid);

        if (existingLoginOptional.isPresent()) {
            return ResponseEntity.ok(existingLoginOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String generateOTP() {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }
}


