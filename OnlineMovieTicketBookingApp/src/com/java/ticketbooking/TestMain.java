package com.java.ticketbooking;

import java.util.Scanner;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoviesDetails m=new MoviesDetails();
		System.out.println("Welocme to IMax Book Your Tickets\n");
		m.showMovie();
		
		
		int noTicket;
		Scanner s=new Scanner(System.in);
			System.out.println("\nEnter 1 for BookTicket and 2 for Exit");
			int input=s.nextInt();
			if(input==1) {
				System.out.println("Select Movie Useing id");
		        int inputid=s.nextInt();
				m.selectmovie(inputid);
				if(m.isAvailable(inputid)) {
			    m.noTicket(inputid);
				}else {
					System.out.println("\nticket not available");
				}
				
			}else
				System.out.println("Exit");
			}
		}

	


