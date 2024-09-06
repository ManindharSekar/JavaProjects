package com.atm_machine;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MoneyTransferService {
	double currentBalance;
	double currentBalance1;

	public void sendMoney(String accountNumber, String reciverAccountNumber, double transferAmount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Query selectQuery = session
				.createQuery("select balance from Statements where acc_num.account_no=:i order by id desc");
		selectQuery.setParameter("i", accountNumber);
		selectQuery.setMaxResults(1);
		List resultList = selectQuery.getResultList();
		if (!resultList.isEmpty()) {
			currentBalance = (double) resultList.get(0);
			System.out.println(currentBalance);
		}
		if (currentBalance > 0 && currentBalance >= transferAmount) {
			double newBalance = currentBalance - transferAmount;
			AccountDetails accountDetails = new AccountDetails();
			accountDetails.setAccount_no(accountNumber);
			Statements statements = new Statements();
			statements.setAcc_noum(accountDetails);
			statements.setDate(LocalDate.now());
			statements.setTime(LocalTime.now());
			statements.setDebit(transferAmount);
			statements.setTransaction(reciverAccountNumber);
			statements.setBalance(newBalance);
			session.save(statements);
			transaction.commit();
			reciveMoney(accountNumber, reciverAccountNumber, transferAmount);
			LoginService loginService = new LoginService();
			loginService.listATMOptions(accountNumber);
		} else {
			System.out.println("insufficient balance");
			LoginService loginService = new LoginService();
			loginService.listATMOptions(accountNumber);
		}

	}

	public void reciveMoney(String accountNumber, String reciverAccountNumber, double transferAmount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Query selectQuery = session
				.createQuery("select balance from Statements where acc_num.account_no=:i order by id desc");
		selectQuery.setParameter("i", reciverAccountNumber);
		selectQuery.setMaxResults(1);
		List resultList = selectQuery.getResultList();
		if (!resultList.isEmpty()) {
			currentBalance = (double) resultList.get(0);
		}
		
		double newBalance1 = currentBalance1 + transferAmount;
		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setAccount_no(reciverAccountNumber);
		Statements statements = new Statements();
		statements.setAcc_noum(accountDetails);
		statements.setDate(LocalDate.now());
		statements.setTime(LocalTime.now());
		statements.setCredit(transferAmount);
		statements.setTransaction(accountNumber);
		statements.setBalance(newBalance1);
		session.save(statements);
		transaction.commit();

	}

}
