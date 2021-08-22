package com.bankingConsole.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String role = "customer";
	private List<Account> accounts;

	public Customer(String userId, String username, String password) {
		super(userId, username, password);
		setAccounts(new ArrayList<Account>());
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "ID: " + getUserId() + " Username: " + getUsername() + " Password: " + getPassword() + " Role: " + role;
		
	}

	public List<Account> getAccounts() {
		return accounts;
	}
	
	public void getAccountstoString() {
		System.out.println("# of Acc: " + accounts.size());
		if (accounts.size() > 0) {
			accounts.forEach(acc -> System.out.println("\n| ID: " 
					+ acc.getId() 
					+ " | Type: " 
					+ acc.getType() 
					+ " | Balance: " 
					+ acc.getAmount() 
					+ " | Status: " 
					+ acc.getStatus() 
					+ " |"));
		}
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
