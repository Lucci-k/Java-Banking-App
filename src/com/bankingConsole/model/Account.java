package com.bankingConsole.model;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private List<String> owners;
	private double amount;
	private String status;
	private List<Transaction> history;
	
	public Account(String id, String type, List<String> owners, double initialAmt, String status, List<Transaction> history) {
		this.id = id;
		this.type = type;
		this.owners = owners;
		this.amount = initialAmt;
		this.status = status;
		this.history = history;
		
	}
	
	public Account() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getOwners() {
		return owners;
	}

	public void setOwners(List<String> owners) {
		this.owners = owners;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double d) {
		this.amount = d;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Transaction> getHistory() {
		return history;
	}

	public void setHistory(List<Transaction> history) {
		this.history = history;
	}
	
}
