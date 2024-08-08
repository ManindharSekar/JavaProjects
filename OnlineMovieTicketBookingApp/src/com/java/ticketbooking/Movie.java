package com.java.ticketbooking;

import java.util.ArrayList;
import java.util.Date;

public class Movie {
	int id;
	String name;
	Date date;
	ArrayList<String> theatre;
	
	int getId() {
		return this.id;
	}
	
	String getName() {
		return this.name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	public static void main(String args[]) {
		Movie raayanMovie = new Movie();
		raayanMovie.setName("Raayan");
		
	}
	
}
