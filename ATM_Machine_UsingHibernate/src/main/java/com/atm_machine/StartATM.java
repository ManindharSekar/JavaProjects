package com.atm_machine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StartATM {

	public static void main(String[] args) {
		System.out.println("Welcome To My Bank ATM.");

		System.out.println("1.User\n2.Admin");
		Scanner scanner = new Scanner(System.in);
		try {
			int chooseOption = scanner.nextInt();
			switch (chooseOption) {
			case 1:
				LoginService loginService = new LoginService();
				loginService.checkAccount();
				break;
			case 2:
				AddAccountsService aas = new AddAccountsService();
				aas.addAccount();
				break;
			default:
				System.out.println("you enter wrong input try again.");
				main(args);

			}
		} catch (InputMismatchException e) {
			System.out.println("you enter wrong input try again.");
			main(args);
		}

	}

}
