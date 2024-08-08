package com.java.atm_machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CheckAccountAndPin {

	public boolean isAccCorrect(int acc_no) {
		try {
			String query = "select* from AccountDetails where account_no=?";
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, acc_no);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean isPinCorrect(int acc_no, int pinno) {
		String query = "select* from AccountDetails where atm_pin=? and account_no=?";
		try {
			// Scanner scanner=new Scanner(System.in);
			// System.out.println("Enter Pin Number");
			// int pinno=scanner.nextInt();
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, pinno);
			ps.setInt(2, acc_no);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

}
