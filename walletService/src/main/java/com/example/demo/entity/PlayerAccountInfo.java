package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity 
public class PlayerAccountInfo {
	
	@Version
	int version;
	
	@Id
	long id;
	@Column
	String name;
	@Column
	int accountNo;
	@Column
	double balance;
	
   
	@OneToMany(mappedBy ="player" ,fetch = FetchType.EAGER,cascade = CascadeType.ALL )
	@JsonManagedReference
	List<TransactionInfo> transactionDetails;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	
	@Override
	public String toString() {
		return "PlayerAccountInfo [id=" + id + ", name=" + name + ", accountNo=" + accountNo + ", balance=" + balance
				+ ", transactionDetails=" + transactionDetails + "]";
	}


	public PlayerAccountInfo(long id, String name, int accountNo, double balance,
			List<TransactionInfo> transactionDetails) {
		super();
		this.id = id;
		this.name = name;
		this.accountNo = accountNo;
		this.balance = balance;
		this.transactionDetails = transactionDetails;
	}


	public PlayerAccountInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	List<TransactionInfo> add(TransactionInfo transaction) {
	//	List<TransactionInfo> transactionList = null;

		if (this.transactionDetails == null)
			this.transactionDetails = new ArrayList<>();

		this.transactionDetails.add(transaction);

		return this.transactionDetails;
	}


	
	  public List<TransactionInfo> getTransactionDetails() { return
	  transactionDetails; }
	 


	public void setTransactionDetails(List<TransactionInfo> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	
	
	
		  
	 
}
