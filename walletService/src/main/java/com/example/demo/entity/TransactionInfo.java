package com.example.demo.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity

public class TransactionInfo {

	@Id
	Long transactionId;
	
	//@JsonIgnore
	@ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="player_id",referencedColumnName = "id")
	@JsonBackReference
	PlayerAccountInfo player;
	
	@Column
	double amount;
	@Column
	String transactionType;
	@Column
	Date transactionDate;
	
	
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public PlayerAccountInfo getPlayer() {
		return this.player;
	}
	public void setPlayer(PlayerAccountInfo player) {
		this.player = player;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public TransactionInfo(Long transactionId, PlayerAccountInfo player, double amount, String transactionType,
			Date transactionDate) {
		super();
		this.transactionId = transactionId;
		this.player = player;
		this.amount = amount;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
	}
	public TransactionInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TransactionInfo [transactionId=" + transactionId + ", amount=" + amount + ", transactionType="
				+ transactionType + ", transactionDate=" + transactionDate + ", player="+player.id+"]";
	}
		
	
	
}
