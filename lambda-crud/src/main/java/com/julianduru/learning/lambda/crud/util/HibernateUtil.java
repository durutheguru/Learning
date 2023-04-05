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
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

@Slf4j
public class HibernateUtil {

	private SessionFactory sessionFactory;


	public void setup() {
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


	private SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
				log.info("Building Session Factory");

				var env = System.getenv("ENV");
				log.info("ENV: {}", env);
				if (StringUtils.isEmpty(env)) {
					env = "local";
				}

				var properties = loadProps(env);
				runMigration(properties);
				initializeSessionFactory(properties);
			}
			else {
				log.info("Returning existing Session Factory");
			}

			return sessionFactory;
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}


	private void initializeSessionFactory(Properties properties) {
		var configuration = new Configuration()
			.configure("hibernate.cfg.xml")
			.addProperties(properties);

		configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
		sessionFactory = configuration.buildSessionFactory();

		log.info("Done initializing Session Factory");
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

		decryptedProperties.putAll(
			Map.of(
				"hibernate.connection.url", System.getenv("DB_URL"),
				"hibernate.connection.username", System.getenv("DB_USER"),
				"hibernate.connection.password", System.getenv("DB_PWD")
			)
		);

		log.debug("Loaded Properties: {}", decryptedProperties);

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


	public Callable<SessionFactory> getSessionFactoryCallable() {
		return () -> sessionFactory;
	}


	public SessionFactory getSessionFactory() throws RuntimeException {
		try {
			return getSessionFactoryCallable().call();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	public void shutdown() throws Exception {
		getSessionFactoryCallable().call().close();
	}


}