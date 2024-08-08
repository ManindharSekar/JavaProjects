package com.java.atm_machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementEnquery {

	public void balance(int acc_no) {
		try {
			String query = "select balance from statements where acc_no=?";
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, acc_no);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("-----------------------------");
				System.out.println("your current balance is: " + rs.getInt("balance"));
				System.out.println("-----------------------------");
			}
			ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
			validateaccandpin.checkChooseOption(acc_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void statement(int acc_no) {
		String query = "select accountdetails.name,accountdetails.account_no,statements.date,statements.time,statements.credit,statements.debit,statements.balance from accountdetails inner join statements on accountdetails.account_no=statements.acc_no where accountdetails.account_no=?";
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, acc_no);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("-----Name: " + rs.getString(1) + "-----");
				System.out.println("-----Account No: " + rs.getInt(2) + "-----");
				System.out.println("--------------------------------------------------------");
				System.out.println("Date    \tTime    \tCredit\tdebit\tbalance");
				System.out.println("--------------------------------------------------------");
			}
			while (rs.next()) {

				System.out.println(rs.getDate(3) + "\t" + rs.getTime(4) + "\t" + rs.getInt(5) + "\t" + rs.getInt(6)
						+ "\t" + rs.getInt(7));
				System.out.println("________________________________________________________");
				System.out.println();
			}
			ValidateAccAndPin validateaccandpin = new ValidateAccAndPin();
			validateaccandpin.checkChooseOption(acc_no);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
