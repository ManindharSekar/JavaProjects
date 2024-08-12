package com.java.ticketbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MoviesDetails {
	int noticket;
	
	public void showMovie() {
		try {
			Connection connection=DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet= statement.executeQuery("select*from movielist");
			System.out.println("__________________________________________________________________________________________________");
			System.out.println("Movie id\tMovie Name\tTicket Price\tShowTime\tShow Date\tAvailable tickets");
			System.out.println("__________________________________________________________________________________________________");
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1)+"\t\t"+resultSet.getString(2)+"          "+resultSet.getInt(3)+"\t\t"+resultSet.getTime(4)+"\t"+resultSet.getDate(5)+"\t\t"+resultSet.getInt(6));
			}
			System.out.println("__________________________________________________________________________________________________");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void selectmovie(int inputid) {
        
        
        String selectQuery="select*from movielist where id=?";
        try {
			Connection connection=DBConnection.getConnection();
			PreparedStatement preparestatement = connection.prepareStatement(selectQuery);
			preparestatement.setInt(1,inputid);
			ResultSet resultset= preparestatement.executeQuery();
			while(resultset.next()) {
				System.out.println("Movie id: "+resultset.getInt(1));
				System.out.println("Movie Name: "+resultset.getString(2));
				System.out.println("Ticket Price: "+resultset.getInt(3));
				System.out.println("Show Time: "+resultset.getTime(4));
				System.out.println("Show Date: "+resultset.getDate(5));
				System.out.println("Available tickets: "+resultset.getInt(6));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
}
	public boolean isAvailable(int input) {
		
		try {
			Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement= connection.prepareStatement("select avl_ticket from movielist where id=? AND avl_ticket>0");
			prepareStatement.setInt(1, input);
			ResultSet resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
			int avl_ticket=resultSet.getInt("avl_ticket");
			if(avl_ticket>0) {
				return true;
			}
			}
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	public void  noTicket(int inputid) {
		try {
			Scanner scanner=new Scanner(System.in);
			System.out.println("\nNo of ticket you want");
			noticket=scanner.nextInt();
			String query="update movielist set avl_ticket=avl_ticket-? where id=? ";
			Connection connection=DBConnection.getConnection();
			PreparedStatement preparestatement = connection.prepareStatement(query);
			preparestatement.setInt(1, noticket);
			preparestatement.setInt(2,inputid);
			preparestatement.executeUpdate();
			System.out.println("-------");
			connection.close();
			scanner.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		totalPrice(inputid,noticket);
		
	}
	
	
	
	public void totalPrice(int inputid,int noticket) {
		int total = 0;
		try {
			String query="select ticketprice from movielist where id=?";
			Connection connection =DBConnection.getConnection();
			PreparedStatement preparestatement = connection.prepareStatement(query);
			preparestatement.setInt(1, inputid);
			ResultSet resultset = preparestatement.executeQuery();
			if(resultset.next()) {
			int ticketprice=resultset.getInt("ticketprice");
			total=ticketprice*noticket;
				System.out.println("Total Price: "+total);
			}
			BookingService booking=new BookingService();
			booking.addBooking(inputid, noticket, total);
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
	
}
