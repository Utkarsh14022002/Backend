package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.persistence.CascadeType;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.LoginRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	@NotNull
	@Column(name="title")
	private String title;
	@NotNull
	@Column(name="first_name")
	private String firstname;
	
	@Column(name="middle_name")
	private String middlename;
	
	@NotNull
	@Column(name="last_name")
	private String lastname;
	@NotNull
	@Column(name="father_name")
	private String fathersname;
	@NotNull
	@Column(name="mobile_number")
	private long mobilenumber;
	@NotNull
	@Column(name="aadhar_number")
	private long aadharnumber;
	@NotNull
	@Column(name="dob")
	private String dob;
	@Column(name="transaction_pin")
	private int transactionpin;
	@NotNull
	@Column(name="balance")
	private long balance;
	@NotNull
	@Column(name="occupation_type")
	private String occupationtype;
	@NotNull
	@Column (name="source_of_income")
	private String sourceofincome;
	@NotNull
	@Column(name="annual_income")
	private long annualincome;
	@NotNull
	@Column(name="account_type")
	private String accounttype;
	@NotNull
	@Column(name="residential_address")
	private String residentialAddress;
	@NotNull
	@Column(name="permanent_address")
	private String permanentAddress;
	


	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", title=" + title + ", firstname=" + firstname + ", middlename="
				+ middlename + ", lastname=" + lastname + ", fathersname=" + fathersname + ", mobilenumber="
				+ mobilenumber + ", aadharnumber=" + aadharnumber + ", dob=" + dob + ", transactionpin="
				+ transactionpin + ", balance=" + balance + ", occupationtype=" + occupationtype + ", sourceofincome="
				+ sourceofincome + ", annualincome=" + annualincome + ", accounttype=" + accounttype
				+ ", residentialAddress=" + residentialAddress + ", permanentAddress=" + permanentAddress + "]";
	}
	

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Login login;
	
	public void setUserIdFromLogin(Login user) {
		this.login=user;
	}
	

	public List<Account> findByUser(Login user,AccountRepository accountRepository){
		return accountRepository.findByLogin(user);
	}

	@JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Payee> payee;


	
	@JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Transaction> transaction;



	
	
}