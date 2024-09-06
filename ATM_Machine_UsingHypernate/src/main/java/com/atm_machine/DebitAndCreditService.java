package com.atm_machine;

import java.beans.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DebitAndCreditService {
	double currentBalance;
	double newBalance;

	public void deposit(String accountNumber, double creditAmount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Query<Double> selectQuery = session.createQuery(
				"select balance from Statements where acc_num.account_no=:i order by id desc", Double.class);
		selectQuery.setParameter("i", accountNumber);
		selectQuery.setMaxResults(1);
		List<Double> resultList = selectQuery.getResultList();
		if (!resultList.isEmpty()) {
			currentBalance = resultList.get(0);
			System.out.println(currentBalance);
		}
		newBalance = currentBalance + creditAmount;

		AccountDetails accountDetails = new AccountDetails();
		accountDetails.setAccount_no(accountNumber);
		Statements statements = new Statements();
		statements.setAcc_noum(accountDetails);
		statements.setTime(LocalTime.now());
		statements.setDate(LocalDate.now());
		statements.setCredit(creditAmount);
		statements.setBalance(newBalance);
		session.save(statements);
		transaction.commit();
		LoginService loginService = new LoginService();
		loginService.listATMOptions(accountNumber);

	}

	public void withdrawl(String accountNumber, double debitAmount) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		Query<Double> selectQuery = session.createQuery(
				"select balance from Statements where acc_num.account_no=:i ORDER BY id DESC ", Double.class);
		selectQuery.setParameter("i", accountNumber);
		selectQuery.setMaxResults(1);
		List<Double> resultList = selectQuery.getResultList();
		if (!resultList.isEmpty()) {
			currentBalance = resultList.get(0);
			System.out.println(currentBalance);
		}
		if (currentBalance > 0) {
			newBalance = currentBalance - debitAmount;

			if (newBalance >= 0) {
				AccountDetails accountDetails = new AccountDetails();
				accountDetails.setAccount_no(accountNumber);
				Statements statements = new Statements();
				statements.setAcc_noum(accountDetails);
				statements.setTime(LocalTime.now());
				statements.setDate(LocalDate.now());
				statements.setDebit(debitAmount);
				statements.setBalance(newBalance);
				session.save(statements);
				transaction.commit();
			} else {
				System.out.println("insufficient balance retry");
				LoginService loginService = new LoginService();
				loginService.listATMOptions(accountNumber);
			}
		} else {
			System.out.println("insufficient balance retry");
			LoginService loginService = new LoginService();
			loginService.listATMOptions(accountNumber);
		}
		LoginService loginService = new LoginService();
		loginService.listATMOptions(accountNumber);
	}

}
