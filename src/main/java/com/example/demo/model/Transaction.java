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

//import org.hibernate.annotations.Columns;


@Entity
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Transaction {
	@Id
	@Column(name="transaction_id")
	private long transactionid;
	@Column(name="to_acc_no")
	private long toaccno;
	@Column(name="amount")
	private long amount;
	@Column(name="date")
	private String date;
	@Column(name="remarks")
	private String remarks;
	@Column(name="mode")
	private String mode;
	
	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "from_acc_no")
	private Account account;
	
	public void setAccountNoFromAccount(Account user)
	{
		this.account = user;
	}
}