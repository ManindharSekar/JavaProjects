package com.java.ticketbooking;

import java.util.Scanner;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoviesDetails moviedetails = new MoviesDetails();
		Booking booking = new Booking();
		System.out.println("Welocme to IMax Book Your Tickets\n");
		moviedetails.showMovie();

		int noTicket;
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nEnter 1 for BookTicket \n------2 for view your booked Ticket \n------3 for Exit");
		int logininput = scanner.nextInt();

		if (logininput == 1) {
			System.out.println("Select Movie Using id");
			int inputid = scanner.nextInt();
			moviedetails.selectmovie(inputid);
			
			if (moviedetails.isAvailable(inputid)) {
				moviedetails.noTicket(inputid);

			} else {
				System.out.println("\nticket not available");
			}

		} else if (logininput == 2) {
			booking.viewBookings();

		} else {
			System.out.println("Exit");
		}

	}
}
