package com.bankingConsole.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import com.bankingConsole.database.Database;
import com.bankingConsole.model.Account;
import com.bankingConsole.model.Customer;
import com.bankingConsole.model.Transaction;

import utils.UniqueStringGenerator;

public class AccountService {
	
	static Database database = Database.getDatabaseInstance();
	
	public static void getAllAccounts() {
		System.out.println("\n|************ ALL ACCOUNTS ************|\n");
		List<Account> accounts = database.getAllAccounts();
		accounts.forEach(acc -> System.out.println("| " 
				+ acc.getId() 
				+ " | " + acc.getType() 
				+ " | " + acc.getOwners() 
				+ " | $" + acc.getAmount() 
				+ " | " + acc.getHistory() 
				+ " | " + acc.getStatus()
				+ " |\n"));
	}
	
	public static void getAllAccountsPendingApproval() {
		System.out.println("\n|************ PENDING APPROVAL ************|\n");
		List<Account> accounts = database.getAllAccounts();
		Stream<Account> pendingAcc = accounts.stream().filter(acc -> acc.getStatus().equals("Pending"));
		pendingAcc.forEach(acc -> System.out.println("| "
				+ acc.getId() 
				+ " | " + acc.getType() 
				+ " | " + acc.getOwners() 
				+ " | $" + acc.getAmount() 
				+ " | " + acc.getHistory() 
				+ " | " + acc.getStatus()
				+ " | \n"));
	}
	
	public static boolean deposit(Scanner scanner) {

		System.out.println("\n|************ DEPOSIT ************|\n");
		System.out.println("Enter the account number you wish to make a deposit to: ");
		String accId = scanner.nextLine();
		
		database.loadAccountsFromFile();
		Optional<Account> userAccount = database.findAccountByAccountNumber(accId);
		
		if(userAccount.isPresent()) {
			System.out.println("How much do you want to deposit?");
			double depositAmount = scanner.nextDouble();
			if (depositAmount < 1) {
				System.out.println("\nError: Amount must be more than 0\n");
				return false;
			}
			double currentBalance = userAccount.get().getAmount();
			userAccount.get().setAmount(currentBalance + depositAmount);
			
			System.out.println("New Balance: $" + userAccount.get().getAmount());
			return database.saveAccountsToFile();
			
		}
		System.out.println("Account not found");
		return false;
	}
	
	public static boolean withdraw(Scanner scanner) {

		System.out.println("\n|************ WITHDRAW ************|\n");
		System.out.println("Enter the account number you wish to make a withdraw from: ");
		String accId = scanner.nextLine();
		
		database.loadAccountsFromFile();
		Optional<Account> userAccount = database.findAccountByAccountNumber(accId);
		
		if(userAccount.isPresent()) {
			System.out.println("How much do you want to withdraw?");
			double withdrawAmount = scanner.nextDouble();
			if (withdrawAmount < 1) {
				System.out.println("\nError: Amount must be more than 0\n");
				return false;
			}
			double currentBalance = userAccount.get().getAmount();
			if (currentBalance < withdrawAmount) {
				System.out.println("\nError: INSUFFICIENT FUNDS\n");
				System.out.println("Current Balance: $" + currentBalance);
				return false;
			}
			userAccount.get().setAmount(currentBalance - withdrawAmount);
			
			System.out.println("New Balance: $" + userAccount.get().getAmount());
			return database.saveAccountsToFile();
			
		}
		System.out.println("Account not found");
		return false;
	}
	
	public static boolean transfer(Scanner scanner) {
		System.out.println("\n|************ TRANSFER ************|\n");
		System.out.println("Enter the account number you wish to make a transfer from: ");
		String from = scanner.nextLine();
		System.out.println("Enter the account number you wish to make a transfer to: ");
		String to = scanner.nextLine();
		System.out.println("Enter the amount you wish to transfer: ");
		double amt = scanner.nextDouble();
		
		if (amt < 1) {
			System.out.println("\nError: Amount must be more than 0\n");
			return false;
		}
		Optional<Account> isFrom = database.findAccountByAccountNumber(from);
		Optional<Account> isTo = database.findAccountByAccountNumber(to);
		
		if (isFrom.isPresent() && isTo.isPresent()) {
			
			double isFromCurrentBalance = isFrom.get().getAmount();
			if (isFromCurrentBalance < amt) {
				System.out.println("\nError: INSUFFICIENT FUNDS\n");
				System.out.println("Current Balance: $" + isFromCurrentBalance);
				return false;
			}
			
			isFrom.get().setAmount(isFromCurrentBalance - amt);
			
			double isToCurrentBalance = isTo.get().getAmount();
			isTo.get().setAmount(isToCurrentBalance + amt);
			
			System.out.println("\nTransfer Successful...\n");
			return database.saveAccountsToFile();
		}
		System.out.println("\nError: Accounts not found, verify correct account numbers");
		return false;
	}
	
	public static boolean createAccount(Scanner scanner, Customer currentCustomer) {
		
		String id = UniqueStringGenerator.getAlphaNumericString(8);
		String id2 = UniqueStringGenerator.getAlphaNumericString(8);
		
		System.out.println("Enter type of account i.e. savings");
		String type = scanner.nextLine();
		
		List<String> li = new ArrayList<String>();
		li.add(currentCustomer.getUsername());
		
		System.out.println("Enter initial deposit amount");
		double initialAmt = scanner.nextDouble();
		
		String status = "Pending";
		
		List<Transaction> li2 = new ArrayList<Transaction>();
		li2.add(new Transaction(id2, "Account Creation", "N/A", "N/A", 0.0));
		
		
		Account newAccount = new Account(id, type, li, initialAmt, status, li2);
		
		return database.addAccount(newAccount);
		
	}
	
	public static boolean approve(Scanner scanner) {
		System.out.println("Enter account id to approve");
		String accId = scanner.nextLine();
		
		return database.updateAccountStatus(accId);
	}
	
}
