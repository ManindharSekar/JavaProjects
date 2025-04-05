package com.atm_machine;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CheckAccountAndPin {

	public boolean validateAccount(String accountNumber) {
		 Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "FROM AccountDetails WHERE account_no =:i";
		Query<AccountDetails> query = session.createQuery(hql, AccountDetails.class);
		query.setParameter("i", accountNumber);
		List<AccountDetails> results = query.getResultList();
		while (results.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean validatePin(String accountNumber, String pinNumber) {
		 Session session = HibernateUtil.getSessionFactory().openSession();
		String selectQuery = "From AccountDetails Where atm_pin=:i and account_no=:j";
		Query<AccountDetails> query = session.createQuery(selectQuery, AccountDetails.class);
		query.setParameter("i", pinNumber);
		query.setParameter("j", accountNumber);
		List<AccountDetails> resultList = query.getResultList();
		while (resultList.isEmpty()) {
			
			return false;
			
		}
		return true;
	}
}