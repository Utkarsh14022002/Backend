package com.example.demo.rest;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.model.Login;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;
import com.example.demo.model.Account;
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
    
    
//    @GetMapping("/user/{userid}")
//    	public ResponseEntity<String> validateUserid(@PathVariable String userid) {
//        	Optional<Login> user = loginRepository.findByUserid(userid);
//  
//        	if (user.isPresent()) {
//        		return ResponseEntity.ok("User ID is valid");
//        	} else {
//        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User ID");
//        	}
//    	}

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, String>> validateUserid(@PathVariable String userId) {
        Optional<Login> user = loginRepository.findByUserid(userId);
        if (user.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User ID is valid");
            response.put("userId", user.get().getUserid());
            response.put("password", user.get().getPassword());
            response.put("email", user.get().getEmailid());
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid User ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
    
    
//    @PostMapping("/verify-otp")
//    public ResponseEntity<Map<String, String>> verifyOTP(@RequestBody Map<String, String> request) {
//        String email = request.get("emailid");
//        String enteredOTP = request.get("otp");
//
//        // Retrieve the previously generated OTP from your backend or database
//        String generatedOTP = perotp; // Replace with your actual logic
//
//        if (enteredOTP.equals(generatedOTP)) {
//            // Return success response
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "OTP verified successfully");
//            return ResponseEntity.ok(response);
//        } else {
//            // Return failure response
//            Map<String, String> response = new HashMap<>();
//            response.put("message", "OTP verification failed");
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

//    @PostMapping("/get-userid")
//    public ResponseEntity<String> getUserId(@RequestBody Map<String, String> request) {
//        String email = request.get("emailid");
//
//        Optional<Login> existingLoginOptional = loginRepository.findByEmailid(email);
//
//        if (existingLoginOptional.isPresent()) {
//            Login existingLogin = existingLoginOptional.get();
//            String userId = existingLogin.getUserid();
//            return ResponseEntity.ok(userId);
//        } else {
//            return ResponseEntity.badRequest().body("Email not found");
//        }
//    }


    
    
//    @PutMapping("/update-userid")
//    public ResponseEntity<String> updateUserId(@RequestBody Map<String, String> request) {
//        String email = request.get("emailid");
//        String newUserId = request.get("newUserId");
//
//        Optional<Login> existingLoginOptional = loginRepository.findByEmailid(email);
//
//        if (existingLoginOptional.isPresent()) {
//            Login login = existingLoginOptional.get();
//            login.setUserid(newUserId);
//            loginRepository.save(login);
//            return ResponseEntity.ok("User ID updated successfully");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


    

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






//public class LoginController {
//	private LoginRepository loginRepository;
//	
//	public LoginController(LoginRepository loginRepository) {
//		super();
//		this.loginRepository = loginRepository;
//
//	}
//	
////	@PostMapping
////	public ResponseEntity<Login> createLogin(@RequestBody Login login) {
////		Login i = loginRepository.save(login);
////		return new ResponseEntity<Login>(i, HttpStatus.CREATED);
////	}
////	
//	
//
//	@PostMapping
//	public ResponseEntity<Login> createLogin(@RequestBody Login login) {
//	    Optional<Login> existingLoginOptional = loginRepository.findByUserid(login.getUserid());
//
//	    if (existingLoginOptional.isPresent()) {
//	        return ResponseEntity.badRequest().body(existingLoginOptional.get());
//	    }
//
//	    Login i = loginRepository.save(login);
//	    return new ResponseEntity<Login>(i, HttpStatus.CREATED);
//	}
//
//	
//	@GetMapping("/{userid}")
//	public ResponseEntity<Login> validateUserId(@PathVariable("userid") String userid) {
//	    Optional<Login> existingLoginOptional = loginRepository.findByUserid(userid);
//	    
//	    if (existingLoginOptional.isPresent()) {
//	        return ResponseEntity.ok(existingLoginOptional.get());
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}
//	
////	@GetMapping("/{userid}")
////	public ResponseEntity<Optional<Login>> findLoginByUserid(@PathVariable("userid") String userid) {
////
////		Optional<Login> inOptional=loginRepository.findByUserid(userid);
////		System.out.println(inOptional);
////		return ResponseEntity.ok(inOptional);
////	}
//
//}