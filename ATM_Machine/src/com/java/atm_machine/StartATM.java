package com.java.atm_machine;

public class StartATM {

	public static void main(String[] args) {
		// AddAccountService addAccountService=new AddAccountService();
		// addAccountService.add_account();
		LoginService loginService = new LoginService();
		System.out.println("Welcome To My Bank ATM.");
		loginService.checkAccount();

	}

}
