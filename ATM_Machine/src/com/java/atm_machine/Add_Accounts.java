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
		int acc_no = s.nextInt();
		System.out.println("Enter Pin no: ");
		int pin_no = s.nextInt();
		ArrayList<Account_details> al = new ArrayList<>();
		al.add(new Account_details(name, acc_no, pin_no));
		try {
			String query = "insert into AccountDetails(name,account_no,atm_pin) values(?,?,?)";
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, acc_no);
			ps.setInt(3, pin_no);
			int row = ps.executeUpdate();
			System.out.println("Your Account has Been Created: " + row);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
