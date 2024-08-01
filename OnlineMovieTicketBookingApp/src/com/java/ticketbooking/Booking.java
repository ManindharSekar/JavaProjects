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
			Booking b=new Booking();
			b.showBookingDetails(inputid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	

	public void showBookingDetails(int inputid) {
		String query="select bookinglist.id,bookinglist.b_time,bookinglist.b_date,bookinglist.no_ticket,bookinglist.total,movielist.mname from bookinglist inner join movielist on movielist.id=bookinglist.b_id where bookinglist.b_id=?";
		try {
			Connection con=DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, inputid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("\nyour id: "+rs.getInt(1));
				System.out.println("booking time: "+rs.getTime(2));
				System.out.println("booking date: "+rs.getDate(3));
				System.out.println("no of ticket: "+rs.getInt(4));
				System.out.println("total price: "+rs.getInt(5));
				System.out.println("movie name: "+rs.getString(6));
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

}
