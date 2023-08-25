package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.repository.LoginRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TestLoginController {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private LoginRepository loginRepository;
	
	@Test
	public void testValidateUserid() {
		String userId = "girija1234";
	}
	
}
