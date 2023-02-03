package com.julianduru.learning.lambda.crud.util;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.Callable;

@Slf4j
public class HibernateUtil {

	private static SessionFactory sessionFactory;

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				if (sessionFactory != null && !sessionFactory.isClosed()) {
					sessionFactory.close();
				}
			} catch (HibernateException e) {
				log.info("Error while closing session factory", e);
			}
		}));

		sessionFactory = buildSessionFactory();
	}


	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
//				var standardRegistry = new StandardServiceRegistryBuilder()
//					.configure("hibernate.cfg.xml")
//					.build();
//
//				var metaData = new MetadataSources(standardRegistry)
//					.getMetadataBuilder()
//					.build();

				var configuration = new Configuration().configure("hibernate.cfg.xml")
					.addPackage("com.julianduru.learning.lambda.crud.models");

				sessionFactory = configuration.buildSessionFactory();
			}

			return sessionFactory;
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}


	public static Callable<SessionFactory> getSessionFactoryCallable() {
		return () -> sessionFactory;
	}


	public static SessionFactory getSessionFactory() throws Exception {
		return getSessionFactoryCallable().call();
	}


	public static void shutdown() throws Exception {
		getSessionFactoryCallable().call().close();
	}


}