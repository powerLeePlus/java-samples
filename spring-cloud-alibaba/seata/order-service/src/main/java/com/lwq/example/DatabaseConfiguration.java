package com.lwq.example;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author lwq
 * @date 2021/6/8 0008
 */
@Configuration
public class DatabaseConfiguration {
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.execute("TRUNCATE TABLE order_tbl");

		return jdbcTemplate;
	}
}
