package com.julianduru.learning.dbintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class DbIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbIntegrationApplication.class, args);
	}


	@Bean
	JdbcPollingChannelAdapter jdbcPollingChannelAdapter(DataSource dataSource) {
		var adapter = new JdbcPollingChannelAdapter(dataSource, "SELECT * FROM CUSTOMER");

		adapter.setRowMapper((RowMapper<Customer>) (rs, rowNum) -> {
			var id = rs.getLong("ID");
			var firstName = rs.getString("FIRST_NAME");
			var lastName = rs.getString("LAST_NAME");
			var email = rs.getString("EMAIL");

			return new Customer(id, firstName, lastName, email);
		});

		return adapter;
	}


	@Bean
	IntegrationFlow jdbcIntegrationFlow(JdbcPollingChannelAdapter adapter) {
		return IntegrationFlow
			.from(adapter, p -> p.poller(pm -> pm.fixedRate(1000)))
			.get();
	}


	record Customer(
		Long id,
		String firstName,
		String lastName,
		String email
	) {}


}
