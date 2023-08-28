package com.example.demo.model;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AccountTests {
	@Autowired
    private AccountRepository accountRepository;

	@Autowired
	private LoginRepository loginRepository;

    @Test
    public void testFindByLoginUserid() {
        // Create test data
    	 Login user = new Login("testUser", "password", 0, "test@example.com", null);
         loginRepository.save(user);
        List<Account> accounts = accountRepository.findByLoginUserid("testUser");

        // Perform the test
 


        // Assert
        assertEquals(0, accounts.size());
    }

}
