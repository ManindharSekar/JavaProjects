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
	int currentbalance;

	public int deposit(int acc_no, int creditamount) {
		try {
			String squery = "select balance from statements where acc_no=?";
			String updateb = "update statements set balance = ? where acc_no = ?";
			String query = "insert into statements(acc_no,date,time,credit,balance) values(?,?,?,?,?)";

			Connection con = DBConnection.getConnection();

			PreparedStatement ps1 = con.prepareStatement(squery);
			ps1.setInt(1, acc_no);
			ResultSet rs = ps1.executeQuery();

			if (rs.next()) {
				currentbalance = rs.getInt("balance");
			}
			int newbalance = currentbalance + creditamount;

			PreparedStatement ps2 = con.prepareStatement(updateb);
			ps2.setInt(1, newbalance);
			ps2.setInt(2, acc_no);
			ps2.setInt(3, creditamount);
			int row1 = ps2.executeUpdate();

			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, acc_no);
			ps.setDate(2, Date.valueOf(LocalDate.now()));
			ps.setTime(3, Time.valueOf(LocalTime.now()));
			ps.setInt(4, creditamount);
			ps.setInt(5, newbalance);
			int row = ps.executeUpdate();
			System.out.println("****Amount Sucessfully Credit in your accont****");
			ValidateAccAndPin vap = new ValidateAccAndPin();
			vap.checkChooseOption(acc_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public int withdrawl(int acc_no, int debitamount) {
		try {
			String q1 = "select balance from statements where acc_no=?";
			String updatequery = "update statements set balance=? where acc_no=?";
			String query = "insert into statements(acc_no,date,time,debit,balance) values(?,?,?,?,?)";

			Connection con = DBConnection.getConnection();

			PreparedStatement ps1 = con.prepareStatement(q1);
			ps1.setInt(1, acc_no);
			ResultSet rs = ps1.executeQuery();

			if (rs.next()) {
				currentbalance = rs.getInt("balance");
			}
			int newbalance = currentbalance - debitamount;

			PreparedStatement ps2 = con.prepareStatement(updatequery);
			ps2.setInt(1, newbalance);
			ps2.setInt(2, acc_no);
			
			int row1 = ps2.executeUpdate();

			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, acc_no);
			ps.setDate(2, Date.valueOf(LocalDate.now()));
			ps.setTime(3, Time.valueOf(LocalTime.now()));
			ps.setInt(4, debitamount);
			ps.setInt(5, newbalance);
			int row = ps.executeUpdate();
			System.out.println("*****Take Amount*****");
			ValidateAccAndPin vap = new ValidateAccAndPin();
			vap.checkChooseOption(acc_no);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;

	}

}
