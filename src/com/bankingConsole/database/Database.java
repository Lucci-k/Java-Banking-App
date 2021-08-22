package com.bankingConsole.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.bankingConsole.model.Transaction;
import com.bankingConsole.model.Account;
import com.bankingConsole.model.Customer;


public class Database {

	private List<Transaction> transactions;
	private List<Account> accounts;
	private List<Customer> customers;
	private static Database databaseInstance = null;
	
	private Database() {
		transactions = new ArrayList<Transaction>();
		accounts = new ArrayList<Account>();
        customers = new ArrayList<Customer>();
	}
	
	public static Database getDatabaseInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }
	
	/**
	 * Accounts
	 * @param newAccount
	 * @return
	 */

    public boolean addAccount(Account newAccount) {
        accounts.add(newAccount);
        return saveAccountsToFile();
    }
    
    public boolean saveAccountsToFile() {
    	//write to file
    	try{
    	    FileOutputStream writeData = new FileOutputStream("accountsdata.ser");
    	    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

    	    writeStream.writeObject(accounts);
    	    writeStream.flush();
    	    writeStream.close();
    	    System.out.println("Writing account to files...");
    	    return true;
    	}catch (IOException e) {
    	    System.out.println("Failed to write account to files");
    	    e.printStackTrace();
    	    return false;
    	}
    }
    
    public boolean loadAccountsFromFile() {
    	try{
    	    FileInputStream readData = new FileInputStream("accountsdata.ser");
    	    ObjectInputStream readStream = new ObjectInputStream(readData);

    	    @SuppressWarnings("unchecked")
			ArrayList<Account> accountsData = (ArrayList<Account>) readStream.readObject();
    	    readStream.close();
    	    System.out.println("Loading accounts...\n");
    	    accounts = accountsData;
    	    return true;
    	}catch (Exception e) {
    	    e.printStackTrace();
    	    return false;
    	}
    }
    
    public Stream<Account> getAccountsByUser(Customer currentCustomer) {
    	System.out.println("Attempting to load accounts from files...");
    	boolean loadingCustomerAccounts = loadAccountsFromFile(); 
    	if (loadingCustomerAccounts) {
    		Stream<Account> temp = accounts.stream().filter(account -> account.getOwners().contains(currentCustomer.getUsername()));
    		return temp;
    	}
		return null;
    }

    

    public List<Account> getAllAccounts() {
    	this.loadAccountsFromFile();
        return accounts;
    }
    
    public boolean updateAccountStatus(String accId) {
    	Optional<Account> accToChange = this.findAccountByAccountNumber(accId);
    	Account newAcc = new Account();
    	if (accToChange.isPresent()) {
    		accToChange.get().setStatus("Approved");
    		newAcc = accToChange.get();
    		accounts.remove(accToChange.get());
    		accounts.add(newAcc);
    		saveAccountsToFile();
    		return true;
    	}
    	return false;
    	
    }

    public Optional<Account> findAccountByAccountNumber(String accId) {
        return accounts.stream()
                .filter(account -> account.getId().equalsIgnoreCase(accId))
                .findFirst();
    }
    
    /**
     * Customers
     * @param customer
     */

    public void addCustomer(Customer customer) {
    	loadCustomersFromFile();
    	System.out.println("Adding customer to instance...");
        customers.add(customer);
    }

    public Optional<Customer> getCustomerByAccountNumber(String accountNumber) {
        return customers.stream().filter(customer -> customer.getAccounts().stream()
                .anyMatch(account -> account.getId().equalsIgnoreCase(accountNumber))).findFirst();
    }

    public Optional<Customer> getCustomerByCustomerId(String customerId) {
        return customers.stream().filter(customer -> customer.getUserId().equalsIgnoreCase(customerId)).findFirst();
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }
    
    public boolean saveCustomersToFile() {
    	//write to file
    	try{
    	    FileOutputStream writeData = new FileOutputStream("customerdata.ser");
    	    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

    	    writeStream.writeObject(customers);
    	    writeStream.flush();
    	    writeStream.close();
    	    System.out.println("Writing customer to files..\n");
    	    return true;
    	}catch (IOException e) {
    	    System.out.println("Error: Failed to write customer to file\n");
    	    e.printStackTrace();
    	    return false;
    	}
    }
    
	public boolean loadCustomersFromFile() {
    	try{
    	    FileInputStream readData = new FileInputStream("customerdata.ser");
    	    ObjectInputStream readStream = new ObjectInputStream(readData);

    	    @SuppressWarnings("unchecked")
			ArrayList<Customer> customersData = (ArrayList<Customer>) readStream.readObject();
    	    readStream.close();
    	    customers = customersData;
    	    return true;
    	}catch (Exception e) {
    	    e.printStackTrace();
    	    return false;
    	}
    }
	
	/**
	 * Transactions
	 */
	
	public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
	
	public List<Transaction> getAllTransaction() {
        return transactions;
    }
}
