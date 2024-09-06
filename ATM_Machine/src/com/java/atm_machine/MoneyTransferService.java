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
	int currentBalance;
	int currentBalance1;

	public void sendMoney(String fromAccount, String toAccount, double sendingAmount) {
		String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String insertQuery = "insert into statements(acc_no,date,time,transaction,debit,balance) values(?,?,?,?,?,?)";
		String updateQuery = "update statements set balance=? where acc_no=? and id ORDER BY id DESC LIMIT 1";

		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement prepareStatement1 = connection.prepareStatement(selectQuery);
			prepareStatement1.setString(1, fromAccount);
			ResultSet resultSet = prepareStatement1.executeQuery();

			if (resultSet.next()) {
				currentBalance = resultSet.getInt("balance");
			}
			if (currentBalance > 0 && currentBalance >= sendingAmount) {

				double newBalance1 = currentBalance - sendingAmount;

				PreparedStatement prepareStatement2 = connection.prepareStatement(insertQuery);
				prepareStatement2.setString(1, fromAccount);
				prepareStatement2.setDate(2, Date.valueOf(LocalDate.now()));
				prepareStatement2.setTime(3, Time.valueOf(LocalTime.now()));
				prepareStatement2.setString(4, toAccount);
				prepareStatement2.setDouble(5, sendingAmount);
				prepareStatement2.setDouble(6, newBalance1);
				prepareStatement2.executeUpdate();

				System.out.println("----------------------");
				System.out.println("****Your amount has been transfered sucssfully****");

				PreparedStatement preparestatement3 = connection.prepareStatement(updateQuery);
				preparestatement3.setDouble(1, newBalance1);
				preparestatement3.setString(2, fromAccount);
				preparestatement3.executeUpdate();

				receiveMoney(fromAccount, toAccount, sendingAmount);
				LoginService loginService = new LoginService();
				loginService.listATMOptions(fromAccount);
			} else {
				System.out.println("insufficient balance");
				LoginService loginService = new LoginService();
				loginService.listATMOptions(fromAccount);
			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void receiveMoney(String fromAccount, String toAccount, double receivingAmount) {
		String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
		String insertQuery = "insert into statements(acc_no,date,time,credit,transaction,balance) values(?,?,?,?,?,?)";
		String updateQuery = "update statements set balance = ? where acc_no = ? and id ORDER BY id DESC LIMIT 1";
		
		try {
			Connection connection = DBConnection.getConnection();

			PreparedStatement prepareStatement1 = connection.prepareStatement(selectQuery);
			prepareStatement1.setString(1, toAccount);
			ResultSet resultSet = prepareStatement1.executeQuery();

			if (resultSet.next()) {
				currentBalance1 = resultSet.getInt("balance");
			}

			double newBalance = currentBalance1 + receivingAmount;

			PreparedStatement prepareStatement2 = connection.prepareStatement(insertQuery);
			prepareStatement2.setString(1, toAccount);
			prepareStatement2.setDate(2, Date.valueOf(LocalDate.now()));
			prepareStatement2.setTime(3, Time.valueOf(LocalTime.now()));
			prepareStatement2.setDouble(4, receivingAmount);
			prepareStatement2.setString(5, toAccount);
			prepareStatement2.setDouble(6, newBalance);
			prepareStatement2.executeUpdate();

			PreparedStatement preparestatement3 = connection.prepareStatement(updateQuery);
			preparestatement3.setDouble(1, newBalance);
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
