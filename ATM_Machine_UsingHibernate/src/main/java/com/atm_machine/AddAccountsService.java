package com.atm_machine;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AddAccountsService {

	public void addAccount() {
		 Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Scanner s = new Scanner(System.in);
		AccountDetails ad = new AccountDetails();
		System.out.println("***Add new Accounts***");
		System.out.println("Enter Account Number");
		String acc_no = s.next();
		ad.setAccount_no(acc_no);
		System.out.println("Enter Name");
		String name = s.next();
		ad.setName(name);
		System.out.println("Enter pin");
		String pin = s.next();
		ad.setAtm_pin(pin);
		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setAccount_no(acc_no);
		accountDetails.setName(name);
		accountDetails.setAtm_pin(pin);
		session.save(accountDetails);
		transaction.commit();
		System.out.println("***Account has been added successfully***");
		session.close();

	}

}
