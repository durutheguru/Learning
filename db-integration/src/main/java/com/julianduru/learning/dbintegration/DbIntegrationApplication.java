package com.julianduru.learning.dbintegration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.integration.jdbc.SqlParameterSourceFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.messaging.MessageHeaders;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@SpringBootApplication
public class DbIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbIntegrationApplication.class, args);
	}


	@Bean
	JdbcPollingChannelAdapter jdbcPollingChannelAdapter(DataSource dataSource) {
		var adapter = new JdbcPollingChannelAdapter(dataSource, "SELECT * FROM CUSTOMER WHERE PROCESSED = FALSE");

		adapter.setRowMapper((RowMapper<Customer>) (rs, rowNum) -> {
			var id = rs.getLong("ID");
			var firstName = rs.getString("FIRST_NAME");
			var lastName = rs.getString("LAST_NAME");
			var email = rs.getString("EMAIL");
			var processed = rs.getBoolean("PROCESSED");

			return new Customer(id, firstName, lastName, email, processed);
		});
		adapter.setUpdateSql("UPDATE CUSTOMER SET PROCESSED = TRUE WHERE ID = :ID");
		adapter.setUpdatePerRow(true);
		adapter.setUpdateSqlParameterSourceFactory(
			input -> {
				if (input instanceof Customer customer) {
					return new MapSqlParameterSource().addValue("ID", customer.id());
				}
				log.debug("Input is not a Customer: {}", input);
				return null;
			}
		);

		return adapter;
	}


	@Bean
	IntegrationFlow jdbcIntegrationFlow(JdbcPollingChannelAdapter adapter) {
		return IntegrationFlow
			.from(adapter, p -> p.poller(pm -> pm.fixedRate(1000)))
			.handle((GenericHandler<List<Customer>>) (payload, headers) -> {
				log.debug("----------------------");
				log.debug("Customers: {}", payload);
				headers.forEach((k, v) -> log.debug("{}: {}", k, v));
				return null;
			})
			.get();
	}


	@Bean
	JdbcMessageHandler jdbcMessageHandler(DataSource dataSource) {
		var updateHandler = new JdbcMessageHandler(dataSource, "UPDATE CUSTOMER SET PROCESSED = TRUE WHERE ID = ?");
		updateHandler.setPreparedStatementSetter((ps, message) -> {
			var customer = (Customer) message.getPayload();
			ps.setInt(1, customer.id().intValue());
			ps.execute();
		});

		return updateHandler;
	}


	record Customer(
		Long id,
		String firstName,
		String lastName,
		String email,
		boolean processed
	) {}


}

