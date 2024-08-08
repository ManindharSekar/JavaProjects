package com.java.atm_machine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class MoneyTransfer {
	int currentbalance;

	public int TransferedAccount(int acc_no, int transferacc, int transferamount) {
		String squery = "select balance from statements where acc_no=?";
		String updateb = "update statements set balance = ? where acc_no = ?";
		String query = "insert into statements(acc_no,date,time,credit,transaction,balance) values(?,?,?,?,?,?)";

		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement preparestatement1 = connection.prepareStatement(squery);
			preparestatement1.setInt(1, acc_no);
			ResultSet rs = preparestatement1.executeQuery();

			if (rs.next()) {
				currentbalance = rs.getInt("balance");
			}
			int newbalance = currentbalance + transferamount;

			PreparedStatement preparestatement2= connection.prepareStatement(updateb);
			preparestatement2.setInt(1, newbalance);
			preparestatement2.setInt(2, transferacc);
			int row1 = preparestatement2.executeUpdate();
			System.out.println("----------------------");

			PreparedStatement preparestatement3= connection.prepareStatement(query);
			preparestatement3.setInt(1, transferacc);
			preparestatement3.setDate(2, Date.valueOf(LocalDate.now()));
			preparestatement3.setTime(3, Time.valueOf(LocalTime.now()));
			preparestatement3.setInt(4, transferamount);
			preparestatement3.setInt(5, acc_no);
			preparestatement3.setInt(6, newbalance);
			int row = preparestatement3.executeUpdate();
			System.out.println("****Amount Transfred to: " + transferacc + "****");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transferAccount(acc_no, transferacc, transferamount);
	}

	public int transferAccount(int acc_no, int transferacc, int transferamount) {
		String q1 = "select balance from statements where acc_no=?";
		String updatequery = "update statements set balance=? where acc_no=?";
		String query1 = "insert into statements(acc_no,date,time,transaction,debit,balance) values(?,?,?,?,?,?)";

		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement preparestatement4 = connection.prepareStatement(q1);
			preparestatement4.setInt(1, acc_no);
			ResultSet rs1 = preparestatement4.executeQuery();

			if (rs1.next()) {
				currentbalance = rs1.getInt("balance");
			}
			int newbalance1 = currentbalance - transferamount;

			PreparedStatement preparestatement5 = connection.prepareStatement(updatequery);
			preparestatement5.setInt(1, newbalance1);
			preparestatement5.setInt(2, acc_no);
			int row2 = preparestatement5.executeUpdate();
			System.out.println("****Your amount has been transfered sucssfully****");

			PreparedStatement preparestatement6 = connection.prepareStatement(query1);
			preparestatement6.setInt(1, acc_no);
			preparestatement6.setDate(2, Date.valueOf(LocalDate.now()));
			preparestatement6.setTime(3, Time.valueOf(LocalTime.now()));
			preparestatement6.setInt(4, transferacc);
			preparestatement6.setInt(5, transferamount);
			preparestatement6.setInt(6, newbalance1);
			int row3 = preparestatement6.executeUpdate();
			System.out.println("----------------------");
			ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
			validateaccandpin.checkChooseOption(acc_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transferamount;
	}

}
