package com.java.ticketbooking;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;
import java.util.Scanner;

public class Booking {
	
	public void viewBookings() {
		String query="select bookinglist.id,movielist.id,movielist.mname,movielist.showdate,movielist.showtime,bookinglist.no_ticket,bookinglist.total from bookinglist inner join movielist on movielist.id=bookinglist.b_id where bookinglist.id=?";
		try {
			Connection connection=DBConnection.getConnection();
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			Scanner scanner=new Scanner(System.in);
			System.out.println("Enter Your Bookig id");
			int userid=scanner.nextInt();
			preparedstatement.setInt(1,userid);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				System.out.println("Your booking id: "+resultset.getInt(1));
				System.out.println("Movie id: "+resultset.getInt(2));
				System.out.println("Movie name: "+resultset.getString(3));
				System.out.println("Show date: "+resultset.getDate(4));
				System.out.println("Show Time: "+resultset.getTime(5));
				System.out.println("Number of Ticket: "+resultset.getInt(6));
				System.out.println("Total Price: "+resultset.getInt(7));
			}
			scanner.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addBooking(int inputid,int noticket,int total) {
		try {
			String query="insert into bookinglist(b_time,b_date,no_ticket,total,b_id) values(?,?,?,?,?)";
			Connection con=DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			Time currentTime = new Time(Calendar.getInstance().getTimeInMillis());
			ps.setTime(1, currentTime);
			Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
			ps.setDate(2, currentDate);
			ps.setInt(3,noticket);
			ps.setInt(4, total);
			ps.setInt(5, inputid);
			int rows = ps.executeUpdate();
			System.out.println("-------");
			Booking booking=new Booking();
			booking.showBookingDetails(inputid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	

	public void showBookingDetails(int inputid) {
		String query="select bookinglist.id,bookinglist.b_time,bookinglist.b_date,bookinglist.no_ticket,bookinglist.total,movielist.mname from bookinglist inner join movielist on movielist.id=bookinglist.b_id where bookinglist.b_id=? order by id desc ";
		try {
			Connection connection=DBConnection.getConnection();
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1, inputid);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				System.out.println("\nyour id: "+resultset.getInt(1));
				System.out.println("booking time: "+resultset.getTime(2));
				System.out.println("booking date: "+resultset.getDate(3));
				System.out.println("no of ticket: "+resultset.getInt(4));
				System.out.println("total price: "+resultset.getInt(5));
				System.out.println("movie name: "+resultset.getString(6));
				System.out.println();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

}
