package com.java.atm_machine;

public class Accountdetails {
	private String name;
	private String acc_no;
	private String pin_no;

	public Accountdetails(String name, String acc_no, String pin_no) {
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
