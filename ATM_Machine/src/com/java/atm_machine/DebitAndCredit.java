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

	public void deposit(String acc_no, double creditamount) {
		try {
			String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			String insertQuery = "insert into statements(acc_no,date,time,credit,balance) values(?,?,?,?,?)";
			String updateQuery = "update statements set balance = ? where acc_no = ? and id ORDER BY id DESC LIMIT 1";
			Connection connection = DBConnection.getConnection();

			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, acc_no);
			ResultSet resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				currentbalance = resultSet.getInt("balance");
			}
			double newbalance = currentbalance + creditamount;

			PreparedStatement prepareStatement1 = connection.prepareStatement(insertQuery);
			prepareStatement1.setString(1, acc_no);
			prepareStatement1.setDate(2, Date.valueOf(LocalDate.now()));
			prepareStatement1.setTime(3, Time.valueOf(LocalTime.now()));
			prepareStatement1.setDouble(4, creditamount);
			prepareStatement1.setDouble(5, newbalance);
			prepareStatement1.executeUpdate();

			PreparedStatement prepareStatement2 = connection.prepareStatement(updateQuery);
			prepareStatement2.setDouble(1, newbalance);
			prepareStatement2.setString(2, acc_no);
			prepareStatement2.executeUpdate();

			System.out.println("****Amount Sucessfully Credit in your account****");
			ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
			validateaccandpin.checkChooseOption(acc_no);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void withdrawl(String acc_no, double debitamount) {
		try {
			String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			String insertQuery = "insert into statements(acc_no,date,time,debit,balance) values(?,?,?,?,?)";
			String updateQuery = "update statements set balance=? where acc_no=? and id ORDER BY id DESC LIMIT 1";
			
			Connection connection = DBConnection.getConnection();

			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, acc_no);
			ResultSet resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				currentbalance = resultSet.getInt("balance");
			}
			if (currentbalance > 0) {
				newbalance = currentbalance - debitamount;

				if (newbalance >=0) {

					PreparedStatement prepareStatement1 = connection.prepareStatement(insertQuery);
					prepareStatement1.setString(1, acc_no);
					prepareStatement1.setDate(2, Date.valueOf(LocalDate.now()));
					prepareStatement1.setTime(3, Time.valueOf(LocalTime.now()));
					prepareStatement1.setDouble(4, debitamount);
					prepareStatement1.setDouble(5, newbalance);
					prepareStatement1.executeUpdate();

					PreparedStatement prepareStatement2 = connection.prepareStatement(updateQuery);
					prepareStatement2.setDouble(1, newbalance);
					prepareStatement2.setString(2, acc_no);
					prepareStatement2.executeUpdate();

					System.out.println("*****Take Amount*****");
					ValidateAccAndPin vap = new ValidateAccAndPin();
					vap.checkChooseOption(acc_no);
				}else {
					System.out.println("insufficient balance retry");
					ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
					validateaccandpin.checkChooseOption(acc_no);
				}
			} else {
				System.out.println("insufficient balance retry");
				ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
				validateaccandpin.checkChooseOption(acc_no);
			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
}
