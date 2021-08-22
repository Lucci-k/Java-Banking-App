package com.bankingConsole;

import java.util.Scanner;

import com.bankingConsole.prompts.BankingConsolePrompt;

public class App {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		BankingConsolePrompt.initialPrompt(scanner);

	}

}
