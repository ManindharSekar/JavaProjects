package com.java.atm_machine;

public class Account_details {
	private String name;
	private int acc_no;
	private int pin_no;

	public Account_details(String name, int acc_no, int pin_no) {
		this.name = name;
		this.acc_no = acc_no;
		this.pin_no = pin_no;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAcc_no(int acc_no) {
		this.acc_no = acc_no;
	}

	public int getAcc_no() {
		return acc_no;
	}

	public void setPin_no(int pin_no) {
		this.pin_no = pin_no;
	}

	public int getPin_no() {
		return pin_no;
	}

}
