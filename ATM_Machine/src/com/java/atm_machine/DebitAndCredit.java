package com.java.atm_machine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Scanner;

public class DebitAndCredit {
	double currentbalance;
	double newbalance;

	public int deposit(String acc_no, double creditamount) {
		try {
			String squery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			String query = "insert into statements(acc_no,date,time,credit,balance) values(?,?,?,?,?)";
			String updateb = "update statements set balance = ? where acc_no = ? and id ORDER BY id DESC LIMIT 1";
			Connection con = DBConnection.getConnection();

			PreparedStatement ps1 = con.prepareStatement(squery);
			ps1.setString(1, acc_no);
			ResultSet rs = ps1.executeQuery();

			if (rs.next()) {
				currentbalance = rs.getInt("balance");
			}
			double newbalance = currentbalance + creditamount;

			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, acc_no);
			ps.setDate(2, Date.valueOf(LocalDate.now()));
			ps.setTime(3, Time.valueOf(LocalTime.now()));
			ps.setDouble(4, creditamount);
			ps.setDouble(5, newbalance);
			int row = ps.executeUpdate();

			PreparedStatement ps2 = con.prepareStatement(updateb);
			ps2.setDouble(1, newbalance);
			ps2.setString(2, acc_no);
			int row1 = ps2.executeUpdate();

			System.out.println("****Amount Sucessfully Credit in your account****");
			ValidateAccAndPin vap = new ValidateAccAndPin();
			vap.checkChooseOption(acc_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public int withdrawl(String acc_no, double debitamount) {
		try {
			String q1 = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			String updatequery = "update statements set balance=? where acc_no=? and id ORDER BY id DESC LIMIT 1";
			String query = "insert into statements(acc_no,date,time,debit,balance) values(?,?,?,?,?)";

			Connection con = DBConnection.getConnection();

			PreparedStatement ps1 = con.prepareStatement(q1);
			ps1.setString(1, acc_no);
			ResultSet rs = ps1.executeQuery();

			if (rs.next()) {
				currentbalance = rs.getInt("balance");
			}
			if (currentbalance > 0) {
				newbalance = currentbalance - debitamount;

				if (newbalance < debitamount) {

					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, acc_no);
					ps.setDate(2, Date.valueOf(LocalDate.now()));
					ps.setTime(3, Time.valueOf(LocalTime.now()));
					ps.setDouble(4, debitamount);
					ps.setDouble(5, debitamount);
					int row = ps.executeUpdate();

					PreparedStatement ps2 = con.prepareStatement(updatequery);
					ps2.setDouble(1, debitamount);
					;
					ps2.setString(2, acc_no);
					int row1 = ps2.executeUpdate();

					System.out.println("*****Take Amount*****");
					ValidateAccAndPin vap = new ValidateAccAndPin();
					vap.checkChooseOption(acc_no);
				}
			} else {
				System.out.println("insufficient balance retry");
				ValidateAccAndPin vap = new ValidateAccAndPin();
				vap.checkChooseOption(acc_no);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;

	}
}
