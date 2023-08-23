package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Account;
import com.example.demo.model.Address;
import com.example.demo.model.Login;

public interface AddressRepository extends JpaRepository<Address,String> {
	
}
