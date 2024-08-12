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

public class DebitAndCreditService {
	double currentBalance;
	double newBalance;

	public void deposit(String accountNumber, double creditAmount) {
		try {
			String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			String insertQuery = "insert into statements(acc_no,date,time,credit,balance) values(?,?,?,?,?)";
			String updateQuery = "update statements set balance = ? where acc_no = ? and id ORDER BY id DESC LIMIT 1";
			Connection connection = DBConnection.getConnection();

			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, accountNumber);
			ResultSet resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				currentBalance = resultSet.getInt("balance");
			}
			double newbalance = currentBalance + creditAmount;

			PreparedStatement prepareStatement1 = connection.prepareStatement(insertQuery);
			prepareStatement1.setString(1, accountNumber);
			prepareStatement1.setDate(2, Date.valueOf(LocalDate.now()));
			prepareStatement1.setTime(3, Time.valueOf(LocalTime.now()));
			prepareStatement1.setDouble(4, creditAmount);
			prepareStatement1.setDouble(5, newbalance);
			prepareStatement1.executeUpdate();

			PreparedStatement prepareStatement2 = connection.prepareStatement(updateQuery);
			prepareStatement2.setDouble(1, newbalance);
			prepareStatement2.setString(2, accountNumber);
			prepareStatement2.executeUpdate();

			System.out.println("****Amount Sucessfully Credit in your account****");
			LoginService loginService = new LoginService();
			loginService.listATMOptions(accountNumber);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void withdrawl(String accountNumber, double debitAmount) {
		try {
			String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			String insertQuery = "insert into statements(acc_no,date,time,debit,balance) values(?,?,?,?,?)";
			String updateQuery = "update statements set balance=? where acc_no=? and id ORDER BY id DESC LIMIT 1";

			Connection connection = DBConnection.getConnection();

			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, accountNumber);
			ResultSet resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				currentBalance = resultSet.getInt("balance");
			}
			if (currentBalance > 0) {
				newBalance = currentBalance - debitAmount;

				if (newBalance >= 0) {

					PreparedStatement prepareStatement1 = connection.prepareStatement(insertQuery);
					prepareStatement1.setString(1, accountNumber);
					prepareStatement1.setDate(2, Date.valueOf(LocalDate.now()));
					prepareStatement1.setTime(3, Time.valueOf(LocalTime.now()));
					prepareStatement1.setDouble(4, debitAmount);
					prepareStatement1.setDouble(5, newBalance);
					prepareStatement1.executeUpdate();

					PreparedStatement prepareStatement2 = connection.prepareStatement(updateQuery);
					prepareStatement2.setDouble(1, newBalance);
					prepareStatement2.setString(2, accountNumber);
					prepareStatement2.executeUpdate();

					System.out.println("*****Take Amount*****");
					LoginService loginService = new LoginService();
					loginService.listATMOptions(accountNumber);
				} else {
					System.out.println("insufficient balance retry");
					LoginService loginService = new LoginService();
					loginService.listATMOptions(accountNumber);
				}
			} else {
				System.out.println("insufficient balance retry");
				LoginService loginService = new LoginService();
				loginService.listATMOptions(accountNumber);
			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
