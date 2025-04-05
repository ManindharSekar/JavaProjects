package com.atm_machine;

import java.beans.Statement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AccountStatementService {

	public void viewBalance(String accountNumber) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Query<Statement> query = session.createQuery(
				"select balance from Statements where acc_num.account_no=:i order by id desc", Statement.class);
		query.setParameter("i", accountNumber);
		query.setMaxResults(1);
		List<Statement> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			System.out.println("-----------------------------");
			System.out.println("Your Current Balance is:" + resultList.get(0));
			System.out.println("-----------------------------");
		} else {
			System.out.println("your balance is nill");
		}
		
		LoginService loginService = new LoginService();
		loginService.listATMOptions(accountNumber);
		session.close();

	}

	public void printStatement(String accountNumber, LoginService loginService) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Query query = session.createQuery(
				"select acc_num.name,acc_num.account_no,date,time,credit,transaction,debit,balance from Statements where acc_num.account_no=:i");
		query.setParameter("i", accountNumber);
		List<Object[]> resultList = query.getResultList();
		System.out.println(
				"_______________________________________________________________________________________________________________");
		System.out.println("name\t       Account no\t  date\t        time\t       credit\ttransaction\tdebit\tbalance");
		System.out.println(
				"_______________________________________________________________________________________________________________");
		for (Object[] obj : resultList) {
			System.out.println();
			System.out.println(obj[0] + "\t" + obj[1] + "\t" + obj[2] + "\t" + obj[3] + "\t" + obj[4] + "\t   " + obj[5]
					+ "    \t" + obj[6] + "\t    " + obj[7]);
		}
		System.out.println(
				"_______________________________________________________________________________________________________________");


		loginService.listATMOptions(accountNumber);
		session.close();

	}

}
