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
			Connection con=DBConnection.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select*from movielist");
			while(rs.next()) {
				System.out.println("Movie id: "+rs.getInt(1));
				System.out.println("Movie Name: "+rs.getString(2));
				System.out.println("Ticket Price: "+rs.getInt(3));
				System.out.println("Show Time: "+rs.getTime(4));
				System.out.println("Show Date: "+rs.getDate(5));
				System.out.println("Available tickets: "+rs.getInt(6));
				System.out.println();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void selectmovie(int inputid) {
        
        
        String query="select*from movielist where id=?";
        try {
			Connection con=DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1,inputid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("Movie id: "+rs.getInt(1));
				System.out.println("Movie Name: "+rs.getString(2));
				System.out.println("Ticket Price: "+rs.getInt(3));
				System.out.println("Show Time: "+rs.getTime(4));
				System.out.println("Show Date: "+rs.getDate(5));
				System.out.println("Available tickets: "+rs.getInt(6));
			}
			con.close();
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Booking b=new Booking();
		b.addBooking(inputid,noticket,total);

	}
}
