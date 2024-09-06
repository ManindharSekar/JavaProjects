package com.atm_machine;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sf;
	static {
		try {
			sf = new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			System.out.println("initial Sessionfactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sf;
	}

}
