package com.java.atm_machine;


public class Test_ATM {

	public static void main(String[] args) {
		// Add_Accounts aa=new Add_Accounts();
		// aa.add_account();
		ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
		System.out.println("Welcome To My Bank ATM.");
		validateaccandpin.checkAccount();

	}

}
