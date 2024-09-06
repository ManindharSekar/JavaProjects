package com.atm_machine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginService {
	public void checkAccount() {
		CheckAccountAndPin checkAccountAndPin = new CheckAccountAndPin();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Account Number");
		String accountNumber = scanner.next();
		if (accountNumber.matches("[0-9]{8}")) {
			if (checkAccountAndPin.validateAccount(accountNumber)) {
				checkPin(accountNumber);

			} else {
				System.out.println("You Entered Wrong Account Number Retry");
				checkAccount();
			}
		} else {
			System.out.println("Account Number is incorrect");
			checkAccount();
		}
		scanner.close();

	}

	public void checkPin(String accountNumber) {
		CheckAccountAndPin checkAccountAndPin = new CheckAccountAndPin();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your pin");
		String pinNumber = scanner.next();
		if (pinNumber.matches("[0-9]{4}")) {
			if (checkAccountAndPin.validatePin(accountNumber, pinNumber)) {
				listATMOptions(accountNumber);
			} else {
				System.out.println("you enter wrong pin please re-enter");
				checkPin(accountNumber);
			}
		} else {
			System.out.println("Pin number is incorrect");
			checkPin(accountNumber);
		}
		scanner.close();
	}

	public void listATMOptions(String accountNumber) {
		try {

			DebitAndCreditService debitAndCreditService = new DebitAndCreditService();
			AccountStatementService accountStatementService = new AccountStatementService();
			MoneyTransferService moneyTransferService = new MoneyTransferService();
			CheckAccountAndPin checkAccountAndPin = new CheckAccountAndPin();
			AddAccountsService addAccountsService = new AddAccountsService();
			System.out.println(
					"1.Withdrawal \n2.Deposit \n3.Balance \n4.Money_Transfer \n5.Statement \n6.Exit");
			System.out.println("Choose any option");
			Scanner scanner = new Scanner(System.in);
			int chooseOption = scanner.nextInt();

			switch (chooseOption) {
			case 1:
				System.out.println("Enter Withdrawl Amount");
				double debitAmount = scanner.nextDouble();
				debitAndCreditService.withdrawl(accountNumber, debitAmount);
				break;
			case 2:
				System.out.println("Enter your deposit amount");
				double creditAmount = scanner.nextDouble();
				debitAndCreditService.deposit(accountNumber, creditAmount);
				break;
			case 3:
				accountStatementService.viewBalance(accountNumber);
				break;
			case 4:
				System.out.println("Enter Transfer Account no");
				String reciverAccountNumber = scanner.next();

				if (checkAccountAndPin.validateAccount(reciverAccountNumber)) {
					System.out.println("Enter amount to transfer");
					double transferAmount = scanner.nextDouble();
					moneyTransferService.sendMoney(accountNumber, reciverAccountNumber, transferAmount);
				} else {
					System.out.println("You Enter Wrong Account Number try again");
					listATMOptions(accountNumber);
				}
				break;
			case 5:
				accountStatementService.printStatement(accountNumber);
				break;
			case 6:
				System.out.println("Exited");
				break;
			default:
				System.out.println("you enter wrong input please try again");
				listATMOptions(accountNumber);
			}
			scanner.close();
		} catch (InputMismatchException e) {
			System.out.println("invalid input try again");
			listATMOptions(accountNumber);
		}

	}

}
