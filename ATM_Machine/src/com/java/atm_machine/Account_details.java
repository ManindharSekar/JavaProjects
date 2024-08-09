package com.java.atm_machine;

public class Account_details {
	private String name;
	private String acc_no;
	private String pin_no;

	public Account_details(String name, String acc_no2, String pin_no2) {
		this.name = name;
		this.acc_no = acc_no2;
		this.pin_no = pin_no2;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public String getAcc_no() {
		return acc_no;
	}

	public void setPin_no(String pin_no) {
		this.pin_no = pin_no;
	}

	public String getPin_no() {
		return pin_no;
	}

}
