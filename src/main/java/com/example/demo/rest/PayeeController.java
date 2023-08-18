package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Payee;
import com.example.demo.repository.PayeeRepository;

@RestController
@RequestMapping("/payees")
public class PayeeController {
	
	@Autowired
	private PayeeRepository payeeRepository;
	
	@PostMapping
	public Payee createPayee(@RequestBody Payee payee)
	{
		payeeRepository.save(payee);
		return payee;
	}

}