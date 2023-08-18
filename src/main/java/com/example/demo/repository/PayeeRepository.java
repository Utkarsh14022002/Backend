package com.example.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Payee;

public interface PayeeRepository extends JpaRepository<Payee,Long> {

}