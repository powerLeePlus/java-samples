package com.lwq.example;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.seata.core.context.RootContext;

/**
 * @author lwq
 * @date 2021/6/8 0008
 */
@RestController
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	private static final String SUCCESS = "SUCCESS";

	private static final String FAIL = "FAIL";

	private final JdbcTemplate jdbcTemplate;

	private Random random;

	public AccountController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.random = new Random();
	}

	@PostMapping(value = "/account", produces = "application/json")
	public String account(String userId, int money) {
		logger.info("Account Service ... xid: " + RootContext.getXID());

		if (random.nextBoolean()) {
			throw new RuntimeException("this is a mock Exception");
		}

		int result = jdbcTemplate.update(
				"UPDATE account_tbl SET money = money - ? WHERE user_id = ?",
				new Object[]{money, userId});
		logger.info("Account Service End ... ");

		if (result == 1) {
			return SUCCESS;
		}
		return FAIL;
	}

}
