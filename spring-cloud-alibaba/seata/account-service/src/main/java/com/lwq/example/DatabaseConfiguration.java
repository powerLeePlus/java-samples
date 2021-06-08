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

		// 测试，初始化数据
		jdbcTemplate.update("delete from account_tbl where user_id = 'U100001'");
		jdbcTemplate.update(
				"insert into account_tbl(user_id, money) values ('U100001', 10000)");

		return jdbcTemplate;
	}
}
