package org.lwq.oauth2.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwq
 * @date 2020/7/9 0009
 */
@SpringBootApplication
@MapperScan("org.lwq.oauth2.server.dao")
public class Oauth2ServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(Oauth2ServerApplication.class, args);
	}
}
