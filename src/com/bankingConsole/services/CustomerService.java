package com.bankingConsole.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import utils.UniqueStringGenerator;

import com.bankingConsole.database.Database;
import com.bankingConsole.model.Account;
import com.bankingConsole.model.Customer;

public class CustomerService {
	
	static Database database = Database.getDatabaseInstance();
	
	public static void viewAccounts(Customer currentCustomer) throws ClassNotFoundException {
		
		Stream<Account> accounts = database.getAccountsByUser(currentCustomer);
		if(accounts != null) {
			ArrayList<Account> temp = new ArrayList<Account>();
			accounts.forEach(account -> temp.add(account));
			
			currentCustomer.setAccounts(temp);
			currentCustomer.getAccountstoString();
		} else {
			System.out.println("There are no accounts");
		}
	}
	
	public static boolean registerCustomer(String username, String password) {
		
		Customer newCustomer = new Customer(UniqueStringGenerator.getAlphaNumericString(8), username, password);
		
		System.out.println("\nAttempting to add customer to database...\n");
		database.addCustomer(newCustomer);
		return database.saveCustomersToFile();
	}
	
	public static Customer signIn(String username, String password) {
		System.out.println(username);
		System.out.println(password);
		
		System.out.println("\nLoading Customer data from database...");
		boolean isLoaded = database.loadCustomersFromFile();
		if (isLoaded) {
			System.out.println("Loading data into instance...");
			ArrayList<Customer> customers = (ArrayList<Customer>) database.getAllCustomers();
			System.out.println("Attempting to locate customer...");
			Optional<Customer> currentCustomer = customers.stream().filter(customer -> customer.getUsername().equals(username)).findFirst();
			if (currentCustomer != null) {
				System.out.println("Found...");
				Stream<Customer> authorize = currentCustomer.stream().filter(customer -> customer.getPassword().equals(password));
				return authorize.findFirst().orElse(null);
			}
			System.out.println("Not found...");
			return null;
		}
		return null;
		
	}
}
