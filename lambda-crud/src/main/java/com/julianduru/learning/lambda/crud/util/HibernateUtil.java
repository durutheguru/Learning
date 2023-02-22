package com.julianduru.learning.lambda.crud.util;


import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.NoIvGenerator;
import org.jasypt.properties.EncryptableProperties;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.Properties;
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
				var env = System.getenv("ENV");
				if (StringUtils.isEmpty(env)) {
					env = "local";
				}

				var properties = loadProps(env);;

				var configuration = new Configuration()
					.configure("hibernate.cfg.xml")
					.addProperties(properties);

				configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

				runMigration(properties);

				sessionFactory = configuration.buildSessionFactory();
			}

			return sessionFactory;
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}


	private static Properties loadProps(String env) throws IOException {
		var props = new EncryptableProperties(encryptor());

		props.load(HibernateUtil.class.getClassLoader().getResourceAsStream(
			String.format("application-%s.properties", env.toLowerCase())
		));

		var decryptedProperties = new Properties();
		props.forEach(
			(k, v) -> decryptedProperties.put(k, props.getProperty(k.toString()))
		);

		return decryptedProperties;
	}


	private static StringEncryptor encryptor() {
		var encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(System.getenv("JASYPT_PWD"));
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		encryptor.setIvGenerator(new NoIvGenerator());

		return encryptor;
	}


	private static DatabaseConnection getLiquibaseDatabaseConnection(Properties connectionProperties) throws Exception {
		var conn = DriverManager.getConnection(
			connectionProperties.getProperty("hibernate.connection.url"),
			connectionProperties.getProperty("hibernate.connection.username"),
			connectionProperties.getProperty("hibernate.connection.password")
		);
		return new JdbcConnection(conn);
	}


	private static void runMigration(Properties properties) throws Exception {
		var liquibase = new Liquibase(
			"db/change-log.main.xml",
			new ClassLoaderResourceAccessor(),
			getLiquibaseDatabaseConnection(properties)
		);

		liquibase.update();
	}


	public static Callable<SessionFactory> getSessionFactoryCallable() {
		return () -> sessionFactory;
	}


	public static SessionFactory getSessionFactory() throws RuntimeException {
		try {
			return getSessionFactoryCallable().call();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}


	public static void shutdown() throws Exception {
		getSessionFactoryCallable().call().close();
	}


}