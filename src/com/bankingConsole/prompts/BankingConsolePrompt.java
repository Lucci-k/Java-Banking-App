package com.bankingConsole.prompts;

import java.util.Scanner;

import com.bankingConsole.model.Customer;
import com.bankingConsole.services.CustomerService;
import com.bankingConsole.services.AccountService;

public class BankingConsolePrompt {
	
	public static void initialPrompt(Scanner scanner) {
		System.out.println("Welcome to Java Banking!");
		System.out.println("Please make a selection:\n");
		System.out.println("1. Sign In");
		System.out.println("2. Register");
		System.out.println("3. Admin as Login");
		System.out.println("0. Exit");
		
		String input = scanner.nextLine();
		
		switch (input) {
		case "1":
			System.out.println("You selected sign in\n");
			signInPrompt(scanner);
			break;
		case "2":
			System.out.println("You selected register\n");
			registerPrompt(scanner);
			break;
		case "3":
			System.out.println("You selected login as Admin\n");
			adminPrompt(scanner);
			break;
		case "0":
			System.out.println("you selected exit\n");
			System.out.println("Good-Bye");
			System.exit(0);
			break;
		default:
			System.out.println("Please select valid option\n");
			initialPrompt(scanner);
		}
		initialPrompt(scanner);
		
	}
	
	public static void adminPrompt(Scanner scanner) {
		System.out.println("Enter admin password");
		String password = scanner.nextLine();
		if (password.equals("password")) {
			adminMenu(scanner, "Great Dark Lord and Savior, Cathulu!");
		} else {
			System.out.println("Wrong password");
		}
		adminPrompt(scanner);
	}
	
	public static void registerPrompt(Scanner scanner) {
		
		System.out.println("Enter your username");
		String username = scanner.nextLine();
		
		System.out.println("Enter password");
		String password = scanner.nextLine();
		
		// Send data to customer service
		boolean success = CustomerService.registerCustomer(username, password);
		if (success) {
			System.out.println("Created account... Please sign in\n");
		} else {
			System.out.println("Failed to create account\n");
		}
		initialPrompt(scanner);
	}
	
	public static void signInPrompt(Scanner scanner) {
		
		System.out.println("Enter your username");
		String username = scanner.nextLine();
		
		System.out.println("Enter password");
		String password = scanner.nextLine();
		
		// Send data to customer service
		Customer currentCustomer = CustomerService.signIn(username, password);
		if (currentCustomer != null) {
			System.out.println("Authorization Granted\n");
			customerMenu(scanner, currentCustomer);
		} else {
			System.out.println("Incorrect Credentials\n");
		}
		initialPrompt(scanner);
	}
	
	public static void customerMenu(Scanner scanner, Customer currentCustomer) {
		
		System.out.println("\nWelcome, " + currentCustomer.getUsername() + "!\n");
		System.out.println("Please make a selection:\n");
		System.out.println("0. Go Back");
		System.out.println("1. View My Accounts");
		System.out.println("2. Apply for new Account");
		
		System.out.println("3. Deposit into Account");
		System.out.println("4. Withdraw from Account");
		System.out.println("5. Transfer Account to Account");
		System.out.println("q. Exit");
		
		String input = scanner.nextLine();
		
		switch (input) {
		case "0":
			// Go Back
			initialPrompt(scanner);
			break;
		case "1":
			try {
				CustomerService.viewAccounts(currentCustomer);
			} catch (ClassNotFoundException e) {
				System.out.println("Failed to retrieve accounts");
				e.printStackTrace();
			}
			break;
		case "2":
			// apply for account from account service
			AccountService.createAccount(scanner, currentCustomer);
			break;
		case "3":
			// Deposit to any account
			AccountService.deposit(scanner);
			break;
		case "4":
			// Withdraw from any account
			AccountService.withdraw(scanner);
			break;
		case "5":
			// Transfer to any account
			AccountService.transfer(scanner);
			break;
		case "q":
			System.out.println("you selected exit\n");
			System.out.println("Good-Bye");
			System.exit(0);
			break;
		default:
			System.out.println("Please select valid option\n");
			customerMenu(scanner, currentCustomer);
		}
		customerMenu(scanner, currentCustomer);
	}
	
	public static void adminMenu(Scanner scanner, String username) {
		System.out.println("\nWelcome, " + username + "!\n");
		System.out.println("Please make a selection:\n");
		System.out.println("0. Go Back");
		System.out.println("1. View All Accounts");
		System.out.println("2. View Pending Accounts");
		System.out.println("3. Deposit into account");
		System.out.println("4. Withdraw from account");
		System.out.println("5. Transfer Account to Account");
		System.out.println("6. Approve Accounts");
		System.out.println("q. Exit");
		
		String input = scanner.nextLine();
		
		switch (input) {
		case "0":
			initialPrompt(scanner);
			// return to prior menu
			break;
		case "1":
			// View all accounts for all customers
			AccountService.getAllAccounts();
			break;
		case "2":
			// view all accounts pending approval
			AccountService.getAllAccountsPendingApproval();
			break;
		case "3":
			// Deposit to any account
			AccountService.deposit(scanner);
			break;
		case "4":
			// Withdraw from any account
			AccountService.withdraw(scanner);
			break;
		case "5":
			// Transfer to any account
			AccountService.transfer(scanner);
			break;
		case "6":
			// Transfer to any account
			AccountService.approve(scanner);
			break;
		case "q":
			System.out.println("you selected exit\n");
			System.out.println("Good-Bye");
			System.exit(0);
			break;
		default:
			System.out.println("Please select valid option\n");
			adminMenu(scanner, username);
		}
		adminMenu(scanner, username);
	}
	
}
