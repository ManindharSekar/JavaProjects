package com.java.atm_machine;

public class StartATM {

	public static void main(String[] args) {
		// Add_Accounts aa=new Add_Accounts();
		// aa.add_account();
		LoginService validateaccandpin = new LoginService();
		System.out.println("Welcome To My Bank ATM.");
		validateaccandpin.checkAccount();

	}

}
