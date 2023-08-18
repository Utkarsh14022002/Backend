package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.hibernate.annotations.Columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Address")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Address {
	@Id
	@Column (name=" typeof")
	private String typeof;
	@Column (name="user_account_no")
	private long useraccountno;
	@Column (name="address_line_1")
	private String addressline1;
	@Column (name="address_line_2")
	private String addressline2;
	@Column (name="landmark")
	private String landmark;
	@Column (name="state")
	private String state;
	@Column(name="city")
	private String city;
	@Column(name="pincode")
	private int pincode;
}