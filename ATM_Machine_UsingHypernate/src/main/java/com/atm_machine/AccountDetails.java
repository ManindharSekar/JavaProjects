package com.atm_machine;

import jakarta.persistence.*;

@Entity
public class AccountDetails {
	@Id
	@Column(nullable = false, unique = true)
	private String account_no;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String atm_pin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getAtm_pin() {
		return atm_pin;
	}

	public void setAtm_pin(String atm_pin) {
		this.atm_pin = atm_pin;
	}

}
