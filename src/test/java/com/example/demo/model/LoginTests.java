package com.example.demo.model;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class LoginTests {


    @Autowired
    private LoginRepository loginRepository;


    @Test
    public void testFindByEmail() {
        // Create test data
        Login user = new Login("testUser", "password", 0, "test@example.com", null);
        loginRepository.save(user);


        // Perform the test
        Optional<Login> foundUser = loginRepository.findByEmailid("test@example.com");


        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUserid());
    }
}


