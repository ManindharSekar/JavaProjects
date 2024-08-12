package com.java.ticketbooking;

import java.util.Scanner;

public class ChooseMovie {
	public void selectMovie() {
		MoviesDetails moviesDetails = new MoviesDetails();
		BookingService bookingService = new BookingService();
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nEnter 1 for BookTicket \n------2 for view your booked Ticket \n------3 for Exit");
		int chooseOption = scanner.nextInt();

		if (chooseOption == 1) {
			System.out.println("Select Movie Using id");
			int selectMovieID = scanner.nextInt();
			moviesDetails.selectmovie(selectMovieID);

			if (moviesDetails.isAvailable(selectMovieID)) {
				moviesDetails.noTicket(selectMovieID);

			} else {
				System.out.println("\nticket not available");
			}

		} else if (chooseOption == 2) {
			bookingService.viewBookings();

		} else if(chooseOption==3){
			System.out.println("Exited");
		}else {
			System.out.println("You Enter Wrong Input Try Again");
			selectMovie();
		}
		scanner.close();
	}

}
