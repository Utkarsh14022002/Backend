package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.filter.JwtTokenFilter;
import com.example.demo.util.JwtTokenUtil;

@SpringBootApplication
public class BankingServiceApplication {
	
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
	
	public static void main(String[] args) {
		SpringApplication.run(BankingServiceApplication.class, args);
	}
//
//	@Bean
//	public JwtTokenFilter jwtTokenFilter() {
//		return new JwtTokenFilter(jwtTokenUtil);
//	}
}
