package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//import org.hibernate.annotations.Columns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "payee")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Payee {
	@Id
	@Column( name="payee_account_id")
	private long payeeaccountno;
	@Column( name="payee_name")
	private String payeename;
	@Column(name="nickname")
	private String nickname;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_account_no")
	private Account account;
	
	public void setAccountNoFromAccount(Account user)
	{
		this.account = user;
	}
}