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

public class BookingService {
	
	public void viewBookings() {
		String selectQuery="select bookinglist.id,movielist.id,movielist.mname,movielist.showdate,movielist.showtime,bookinglist.no_ticket,bookinglist.total from bookinglist inner join movielist on movielist.id=bookinglist.b_id where bookinglist.id=?";
		try {
			Connection connection=DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			System.out.println("Enter Your Bookig id");
			Scanner scanner=new Scanner(System.in);
			int userID = scanner.nextInt();
			preparedStatement.setInt(1,userID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				System.out.println("Your booking id: "+resultSet.getInt(1));
				System.out.println("Movie id: "+resultSet.getInt(2));
				System.out.println("Movie name: "+resultSet.getString(3));
				System.out.println("Show date: "+resultSet.getDate(4));
				System.out.println("Show Time: "+resultSet.getTime(5));
				System.out.println("Number of Ticket: "+resultSet.getInt(6));
				System.out.println("Total Price: "+resultSet.getInt(7));
			}else {
				System.out.println("you enter wrong input try again");
				viewBookings();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addBooking(int inputid,int noticket,int total) {
		try {
			String insertQuery="insert into bookinglist(b_time,b_date,no_ticket,total,b_id) values(?,?,?,?,?)";
			Connection con=DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(insertQuery);
			Time currentTime = new Time(Calendar.getInstance().getTimeInMillis());
			ps.setTime(1, currentTime);
			Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
			ps.setDate(2, currentDate);
			ps.setInt(3,noticket);
			ps.setInt(4, total);
			ps.setInt(5, inputid);
			int rows = ps.executeUpdate();
			System.out.println("-------");
			BookingService booking=new BookingService();
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
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, inputid);
			ResultSet resultset = preparedStatement.executeQuery();
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
