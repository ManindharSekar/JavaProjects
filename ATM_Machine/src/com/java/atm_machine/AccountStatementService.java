package com.java.atm_machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountStatementService {

	public void viewBalance(String accountNumber) {
		try {
			String selectQuery = "select balance from statements where acc_no=? and id ORDER BY id DESC LIMIT 1";
			Connection connection = DBConnection.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, accountNumber);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("-----------------------------");
				System.out.println("your current balance is: " + resultSet.getDouble("balance"));
				System.out.println("-----------------------------");
			} else {
				System.out.println("Your balance is: 0");
			}
			LoginService loginService = new LoginService();
			loginService.listATMOptions(accountNumber);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void printStatement(String accountNumber) {
		String selectQuery = "select accountdetails.name,accountdetails.account_no,statements.date,statements.time,statements.credit,statements.transaction,statements.debit,statements.balance from accountdetails inner join statements on accountdetails.account_no=statements.acc_no where accountdetails.account_no=?";
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, accountNumber);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				System.out.println("-----Name: " + resultSet.getString(1) + "-----");
				System.out.println("-----Account No: " + resultSet.getString(2) + "-----");
				System.out.println("--------------------------------------------------------------------");
				System.out.println("Date    \tTime    \tCredit\tTransaction\t debit\tbalance");
				System.out.println("---------------------------------------------------------------------");
			}
			while (resultSet.next()) {

				System.out.println(resultSet.getDate(3) + "\t" + resultSet.getTime(4) + "\t" + resultSet.getDouble(5)
						+ "\t" + resultSet.getInt(6) + "    " + resultSet.getDouble(7) + "    "
						+ resultSet.getDouble(8));
				System.out.println("_____________________________________________________________________");
				System.out.println();
			}

			LoginService loginService = new LoginService();
			loginService.listATMOptions(accountNumber);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
