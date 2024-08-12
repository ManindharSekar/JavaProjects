package com.java.atm_machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementEnquery {

	public void balance(String acc_no) {
		try {
			String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			Connection connection = DBConnection.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, acc_no);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("-----------------------------");
				System.out.println("your current balance is: " + resultSet.getDouble("balance"));
				System.out.println("-----------------------------");
			} else {
				System.out.println("Your balance is: 0");
			}
			ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
			validateaccandpin.checkChooseOption(acc_no);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void statement(String acc_no) {
		String selectQuery = "select accountdetails.name,accountdetails.account_no,statements.date,statements.time,statements.credit,statements.debit,statements.balance from accountdetails inner join statements on accountdetails.account_no=statements.acc_no where accountdetails.account_no=?";
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, acc_no);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("-----Name: " + resultSet.getString(1) + "-----");
				System.out.println("-----Account No: " + resultSet.getString(2) + "-----");
				System.out.println("--------------------------------------------------------");
				System.out.println("Date    \tTime    \tCredit\tdebit\tbalance");
				System.out.println("--------------------------------------------------------");
			}
			while (resultSet.next()) {

				System.out.println(resultSet.getDate(3) + "\t" + resultSet.getTime(4) + "\t" + resultSet.getDouble(5)
						+ "\t" + resultSet.getDouble(6) + "\t" + resultSet.getDouble(7));
				System.out.println("________________________________________________________");
				System.out.println();
			}
			System.out.println("Your balance is: 0");
			ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
			validateaccandpin.checkChooseOption(acc_no);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
