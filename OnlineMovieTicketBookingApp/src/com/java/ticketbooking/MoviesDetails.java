package com.java.ticketbooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MoviesDetails {
	int noticket;
	
	public boolean isAvailable(int input) {
		
		try {
			Connection con=DBConnection.getConnection();
			PreparedStatement ps= con.prepareStatement("select avl_ticket from movielist where id=? AND avl_ticket>0");
			ps.setInt(1, input);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
			int avl_ticket=rs.getInt("avl_ticket");
			if(avl_ticket>0&&avl_ticket>noticket) {
				return true;
			}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	
	
	public void showMovie() {
		try {
			Connection connection=DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultset= statement.executeQuery("select*from movielist");
			while(resultset.next()) {
				System.out.println("Movie id: "+resultset.getInt(1));
				System.out.println("Movie Name: "+resultset.getString(2));
				System.out.println("Ticket Price: "+resultset.getInt(3));
				System.out.println("Show Time: "+resultset.getTime(4));
				System.out.println("Show Date: "+resultset.getDate(5));
				System.out.println("Available tickets: "+resultset.getInt(6));
				System.out.println();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void selectmovie(int inputid) {
        
        
        String query="select*from movielist where id=?";
        try {
			Connection connection=DBConnection.getConnection();
			PreparedStatement preparestatement = connection.prepareStatement(query);
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
	public void  noTicket(int inputid) {
		try {
			Scanner s=new Scanner(System.in);
			System.out.println("\nNo of ticket you want");
			noticket=s.nextInt();
			String query="update movielist set avl_ticket=avl_ticket-? where id=? ";
			Connection con=DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, noticket);
			ps.setInt(2,inputid);
			int row = ps.executeUpdate();
			System.out.println("-------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		totalPrice(inputid,noticket);
		
	}
	public void totalPrice(int inputid,int noticket) {
		int total = 0;
		try {
			String query="select ticketprice from movielist where id=?";
			Connection con =DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, inputid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
			int ticketprice=rs.getInt("ticketprice");
			total=ticketprice*noticket;
				System.out.println("Total Price: "+total);
			}
			Booking b=new Booking();
			b.addBooking(inputid, noticket, total);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
	
}
