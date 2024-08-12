package com.java.atm_machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CheckAccountAndPin {

	public boolean validateAccount(String acc_no) {
		try {
			String selectQuery = "select* from AccountDetails where account_no=?";
			Connection connection = DBConnection.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, acc_no);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean validatePin(String acc_no, String pinno) {
		String selectQuery = "select* from AccountDetails where atm_pin=? and account_no=?";
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(selectQuery);
			prepareStatement.setString(1, pinno);
			prepareStatement.setString(2, acc_no);
			ResultSet resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

}
