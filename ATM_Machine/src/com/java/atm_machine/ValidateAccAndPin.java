package com.java.atm_machine;

import java.util.Scanner;

public class ValidateAccAndPin {

	public void checkAccountAndPin() {
		CheckAccountAndPin checkaccountnadpin= new CheckAccountAndPin();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Account Number");
		int acc_no = scanner.nextInt();
		System.out.println("Enter your pin");
		int pinno = scanner.nextInt();
		if (checkaccountnadpin.isAccCorrect(acc_no)) {
			if (checkaccountnadpin.isPinCorrect(acc_no, pinno)) {
				checkChooseOption(acc_no);
			} else {
				System.out.println("you enter wrong pin please re-enter");
				checkAccountAndPin();
			}
		} else {
			System.out.println("You Entered Wrong Account Number Retry");
			checkAccountAndPin();
		}

	}

	public void checkChooseOption(int acc_no) {

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
			int depitamount = scanner.nextInt();
			debitandcredit.withdrawl(acc_no, depitamount);
			break;
		case 2:
			System.out.println("Enter your deposit amount");
			int creditamount = scanner.nextInt();
			debitandcredit.deposit(acc_no, creditamount);
			break;
		case 3:
			statementenquery.balance(acc_no);
			break;
		case 4:
			System.out.println("Enter Transfer Account no");
			int transferacc = scanner.nextInt();
			System.out.println("Enter amount to transfer");
			int transferamount = scanner.nextInt();
			moneytransfer.TransferedAccount(acc_no, transferacc, transferamount);
			break;
		case 5:
			statementenquery.statement(acc_no);
			break;
		case 6:
			System.out.println("Exit");
			break;
		default:
			System.out.println("you enter wrong input please try again");
			checkChooseOption(acc_no);
		}
	}

}
