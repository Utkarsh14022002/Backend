package com.example.demo.model;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.PayeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PayeeTests {
	
	 @Autowired
	    private PayeeRepository payeeRepository;


	    @Test
	    public void testSetAccountNoFromAccount() {



	        // Perform the test
	        List<Payee> payees = payeeRepository.findByAccountAccountNo(22331213);


	        // Assert
	        assertEquals(1, payees.size());
	    }

}
