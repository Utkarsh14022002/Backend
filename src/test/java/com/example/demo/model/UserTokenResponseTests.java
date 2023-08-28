package com.example.demo.model;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserTokenResponseTests {


    @Test
    public void testUserTokenResponse() {
        // Create test data
        Login user = new Login("testUser", "password", 0, "test@example.com", null);
        String token = "testToken";


        // Perform the test
        UserTokenResponse response = new UserTokenResponse(token, Optional.of(user));


        // Assert
        assertEquals(token, response.getToken());
        assertEquals(user, response.getUser().orElse(null));
    }

}
