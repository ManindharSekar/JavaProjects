package com.java.atm_machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Add_Accounts {

	public void add_account() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Name: ");
		String name = s.next();
		System.out.println("Enter Account no:");
		String acc_no = s.next();
		System.out.println("Enter Pin no: ");
		String pin_no = s.next();
		if (acc_no.matches("[0-9]{8}")) {
			if (pin_no.matches("[0-9]{4}")) {
				ArrayList<Account_details> al = new ArrayList<>();
				al.add(new Account_details(name, acc_no, pin_no));
				try {
					String query = "insert into AccountDetails(name,account_no,atm_pin) values(?,?,?)";
					Connection con = DBConnection.getConnection();
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, name);
					ps.setString(2, acc_no);
					ps.setString(3, pin_no);
					int row = ps.executeUpdate();
					System.out.println("Your Account has Been Created: " + row);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("pin no must have 4 digits");
			}
		} else {
			System.out.println("Account number must have 8 digits");
		}

	}

}
