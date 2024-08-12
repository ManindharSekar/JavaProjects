package com.java.atm_machine;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class MoneyTransferService {
	int currentbalance;
	int currentbalance1;

	public void transferMoney(String fromAccount, String toAccount, double transferamount) {
		String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String updateQuery = "update statements set balance=? where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String insertQuery = "insert into statements(acc_no,date,time,transaction,debit,balance) values(?,?,?,?,?,?)";

		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement preparestatement1 = connection.prepareStatement(selectQuery);
			preparestatement1.setString(1, fromAccount);
			ResultSet resultSet = preparestatement1.executeQuery();

			if (resultSet.next()) {
				currentbalance = resultSet.getInt("balance");
			}
			if (currentbalance > 0 && currentbalance >= transferamount) {

				double newbalance1 = currentbalance - transferamount;

				PreparedStatement preparestatement2 = connection.prepareStatement(insertQuery);
				preparestatement2.setString(1, fromAccount);
				preparestatement2.setDate(2, Date.valueOf(LocalDate.now()));
				preparestatement2.setTime(3, Time.valueOf(LocalTime.now()));
				preparestatement2.setString(4, toAccount);
				preparestatement2.setDouble(5, transferamount);
				preparestatement2.setDouble(6, newbalance1);
				preparestatement2.executeUpdate();

				System.out.println("----------------------");
				System.out.println("****Your amount has been transfered sucssfully****");

				PreparedStatement preparestatement3 = connection.prepareStatement(updateQuery);
				preparestatement3.setDouble(1, newbalance1);
				preparestatement3.setString(2, fromAccount);
				preparestatement3.executeUpdate();

				TransferedMoney(fromAccount, toAccount, transferamount);
				LoginService validateaccandpin = new LoginService();
				validateaccandpin.listATMOptions(fromAccount);
			} else {
				System.out.println("insufficient balance");
				LoginService validateaccandpin = new LoginService();
				validateaccandpin.listATMOptions(fromAccount);
			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void TransferedMoney(String fromAccount, String toAccount, double transferamount) {
		String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String updateQuery = "update statements set balance = ? where acc_no = ? and id ORDER BY id DESC LIMIT 1";
		String insertQuery = "insert into statements(acc_no,date,time,credit,transaction,balance) values(?,?,?,?,?,?)";

		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement preparestatement1 = connection.prepareStatement(selectQuery);
			preparestatement1.setString(1, toAccount);
			ResultSet resultSet = preparestatement1.executeQuery();

			if (resultSet.next()) {
				currentbalance1 = resultSet.getInt("balance");
			}

			double newbalance = currentbalance1 + transferamount;

			PreparedStatement preparestatement2 = connection.prepareStatement(insertQuery);
			preparestatement2.setString(1, toAccount);
			preparestatement2.setDate(2, Date.valueOf(LocalDate.now()));
			preparestatement2.setTime(3, Time.valueOf(LocalTime.now()));
			preparestatement2.setDouble(4, transferamount);
			preparestatement2.setString(5, toAccount);
			preparestatement2.setDouble(6, newbalance);
			preparestatement2.executeUpdate();

			PreparedStatement preparestatement3 = connection.prepareStatement(updateQuery);
			preparestatement3.setDouble(1, newbalance);
			preparestatement3.setString(2, toAccount);
			preparestatement3.executeUpdate();
			System.out.println("****Amount Transfred to: " + toAccount + "****");
			System.out.println("----------------------");
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
