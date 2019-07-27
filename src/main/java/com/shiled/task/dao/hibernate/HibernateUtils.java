package com.shiled.task.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * 
 * @author assaf
 * Building a session factory is a heavy request, created on startup.
 * 
 */
public class HibernateUtils {
	private static SessionFactory sessionFactory;
	
	public static void init() {
		try {
			sessionFactory = new Configuration()
					.configure().buildSessionFactory();
		}catch (Exception e) {
			throw new ExceptionInInitializerError(
					"Failed to create session factory: "+e.getMessage());
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	/**
	 * Closing the session factory
	 */
	public static void shutDown() {
		getSessionFactory().close();
	}
}
