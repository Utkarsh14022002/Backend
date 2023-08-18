package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;

@RestController
@RequestMapping("/addresss")
public class AddressController {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@PostMapping
	public Address createAddress(@RequestBody Address address)
	{
		addressRepository.save(address);
		return address;
	}

}