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

		// 初始化
		jdbcTemplate.update("delete from storage_tbl where commodity_code = 'C00321'");
		jdbcTemplate.update(
				"insert into storage_tbl(commodity_code, count) values ('C00321', 100)");

		return jdbcTemplate;

	}
}
