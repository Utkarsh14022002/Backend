package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.CascadeType;


import com.example.demo.repository.LoginRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//import org.hibernate.annotations.Columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Account {
	@Id
	@Column(name="account_no")
	private long accountNo;
	@Column(name="title")
	private String title;
	@Column(name="first_name")
	private String firstname;
	@Column(name="middle_name")
	private String middlename;
	@Column(name="last_name")
	private String lastname;
	@Column(name="father_name")
	private String fathersname;
	@Column(name="mobile_number")
	private long mobilenumber;
	@Column(name="aadhar_number")
	private long aadharnumber;
	@Column(name="dob")
	private String dob;
	@Column(name="transaction_pin")
	private int transactionpin;
	@Column(name="balance")
	private long balance;
	@Column(name="occupation_type")
	private String occupationtype;
	@Column (name="source_of_income")
	private String sourceofincome;
	@Column(name="annual_income")
	private long annualincome;
	@Column(name="account_type")
	private String accounttype;
	@Column(name="residential_address")
	private String residentialAddress;
	@Column(name="permanent_address")
	private String permanentAddress;
	

//	@Override
//	public String toString() {
//		return "Account [accountNo=" + accountNo + ", title=" + title + ", firstname=" + firstname + ", middlename="
//				+ middlename + ", lastname=" + lastname + ", fathersname=" + fathersname + ", mobilenumber="
//				+ mobilenumber + ", aadharnumber=" + aadharnumber + ", dob=" + dob + ", transactionpin="
//				+ transactionpin + ", balance=" + balance + ", occupationtype=" + occupationtype + ", sourceofincome="
//				+ sourceofincome + ", annualincome=" + annualincome + ", accounttype=" + accounttype
//				+ ", residentialAddress=" + residentialAddress + ", permanentAddress=" + permanentAddress + "]";
//	}
	
<<<<<<< HEAD
	//s
//	@JsonProperty("userid")
=======

>>>>>>> 840362a4bf3859d9496c228f743400cd8b4c295d
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Login login;
	
	public void setUserIdFromLogin(Login user) {
		this.login=user;
	}



	
	
}