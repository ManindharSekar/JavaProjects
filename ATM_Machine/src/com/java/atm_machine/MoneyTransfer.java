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
	int currentbalance1;

	public void transferAccount(String acc_no, String transferacc, double transferamount) {
		String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String updateQuery = "update statements set balance=? where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String insertQuery = "insert into statements(acc_no,date,time,transaction,debit,balance) values(?,?,?,?,?,?)";

		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement preparestatement1 = connection.prepareStatement(selectQuery);
			preparestatement1.setString(1, acc_no);
			ResultSet resultSet = preparestatement1.executeQuery();

			if (resultSet.next()) {
				currentbalance = resultSet.getInt("balance");
			}
			if (currentbalance > 0 && currentbalance >= transferamount) {

				double newbalance1 = currentbalance - transferamount;

				PreparedStatement preparestatement2 = connection.prepareStatement(insertQuery);
				preparestatement2.setString(1, acc_no);
				preparestatement2.setDate(2, Date.valueOf(LocalDate.now()));
				preparestatement2.setTime(3, Time.valueOf(LocalTime.now()));
				preparestatement2.setString(4, transferacc);
				preparestatement2.setDouble(5, transferamount);
				preparestatement2.setDouble(6, newbalance1);
				preparestatement2.executeUpdate();
				
				System.out.println("----------------------");
				System.out.println("****Your amount has been transfered sucssfully****");

				PreparedStatement preparestatement3 = connection.prepareStatement(updateQuery);
				preparestatement3.setDouble(1, transferamount);
				preparestatement3.setString(2, acc_no);
				preparestatement3.executeUpdate();

				TransferedAccount(acc_no, transferacc, transferamount);
				ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
				validateaccandpin.checkChooseOption(acc_no);
			} else {
				System.out.println("insufficient balance");
				ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
				validateaccandpin.checkChooseOption(acc_no);
			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void TransferedAccount(String acc_no, String transferacc, double transferamount) {
		String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String updateQuery = "update statements set balance = ? where acc_no = ? and id ORDER BY id DESC LIMIT 1";
		String insertQuery = "insert into statements(acc_no,date,time,credit,transaction,balance) values(?,?,?,?,?,?)";

		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement preparestatement1 = connection.prepareStatement(selectQuery);
			preparestatement1.setString(1, transferacc);
			ResultSet resultSet = preparestatement1.executeQuery();

			if (resultSet.next()) {
				currentbalance1 = resultSet.getInt("balance");
			}

			double newbalance = currentbalance1 + transferamount;

			PreparedStatement preparestatement2 = connection.prepareStatement(insertQuery);
			preparestatement2.setString(1, transferacc);
			preparestatement2.setDate(2, Date.valueOf(LocalDate.now()));
			preparestatement2.setTime(3, Time.valueOf(LocalTime.now()));
			preparestatement2.setDouble(4, transferamount);
			preparestatement2.setString(5, acc_no);
			preparestatement2.setDouble(6, newbalance);
			preparestatement2.executeUpdate();

			PreparedStatement preparestatement3 = connection.prepareStatement(updateQuery);
			preparestatement3.setDouble(1, newbalance);
			preparestatement3.setString(2, transferacc);
			preparestatement3.executeUpdate();
			System.out.println("****Amount Transfred to: " + transferacc + "****");
			System.out.println("----------------------");
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
