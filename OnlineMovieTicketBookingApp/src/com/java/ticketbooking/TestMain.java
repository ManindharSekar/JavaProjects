package com.java.ticketbooking;

import java.util.Scanner;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welocme to IMax Book Your Tickets\n");
		MoviesDetails moviesDetails = new MoviesDetails();
		moviesDetails.showMovie();
		ChooseMovie chooseMovie= new ChooseMovie();
		chooseMovie.selectMovie();

	}
}
