package com.atm_machine;

public class StartATM {

	public static void main(String[] args) {

		// AddAccountsService aas=new AddAccountsService();
		// aas.addAccount();

		LoginService loginService = new LoginService();
		System.out.println("Welcome To My Bank ATM.");
		loginService.checkAccount();

	}

}
