package com.java.atm_machine;

import java.util.Scanner;

public class ValidateAccAndPin {

	public void checkAccount() {
		CheckAccountAndPin checkaccountnadpin = new CheckAccountAndPin();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Account Number");
		String acc_no = scanner.next();
		if (acc_no.matches("[0-9]{8}")) {
			if (checkaccountnadpin.isAccCorrect(acc_no)) {
				checkPin(acc_no);

			} else {
				System.out.println("You Entered Wrong Account Number Retry");
				checkAccount();
			}
		} else {
			System.out.println("Account Number is incorrect");
			checkAccount();
		}

	}

	public void checkPin(String acc_no) {
		CheckAccountAndPin checkaccountnadpin = new CheckAccountAndPin();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your pin");
		String pinno = scanner.next();
		if (pinno.matches("[0-9]{4}")) {
			if (checkaccountnadpin.isPinCorrect(acc_no, pinno)) {
				checkChooseOption(acc_no);
			} else {
				System.out.println("you enter wrong pin please re-enter");
				checkPin(acc_no);
			}
		} else {
			System.out.println("Pin number is incorrect");
			checkPin(acc_no);
		}
	}

	public void checkChooseOption(String acc_no) {

		DebitAndCredit debitandcredit = new DebitAndCredit();
		StatementEnquery statementenquery = new StatementEnquery();
		MoneyTransfer moneytransfer = new MoneyTransfer();
		System.out.println("1.Withdrawal \n2.Deposit \n3.Balance \n4.Money_Transfer \n5.Statement \n6.exit");
		System.out.println("Choose any option");
		Scanner scanner = new Scanner(System.in);
		int chooseOption = scanner.nextInt();

		switch (chooseOption) {
		case 1:
			System.out.println("Enter Withdrawl Amount");
			double depitamount = scanner.nextDouble();
			debitandcredit.withdrawl(acc_no, depitamount);
			break;
		case 2:
			System.out.println("Enter your deposit amount");
			double creditamount = scanner.nextDouble();
			debitandcredit.deposit(acc_no, creditamount);
			break;
		case 3:
			statementenquery.balance(acc_no);
			break;
		case 4:
			System.out.println("Enter Transfer Account no");
			String transferacc = scanner.next();
			System.out.println("Enter amount to transfer");
			double transferamount = scanner.nextDouble();
			moneytransfer.transferAccount(acc_no, transferacc, transferamount);
			break;
		case 5:
			statementenquery.statement(acc_no);
			break;
		case 6:
			System.out.println("Exited");
			break;
		default:
			System.out.println("you enter wrong input please try again");
			checkChooseOption(acc_no);
		}
	}

}
