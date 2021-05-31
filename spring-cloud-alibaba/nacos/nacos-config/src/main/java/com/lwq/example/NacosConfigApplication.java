package com.lwq.example;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;

/**
 * @author lwq
 * @date 2021/5/28 0028
 */
@SpringBootApplication
public class NacosConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosConfigApplication.class, args);
	}

	@Bean
	public UserConfig userConfig() {
		return new UserConfig();
	}

	@ConfigurationProperties(prefix = "user")
	static class UserConfig {

		private int age;

		private String name;

		private String hr;

		private Map<String, Object> map;

		private List<User> users;

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHr() {
			return hr;
		}

		public void setHr(String hr) {
			this.hr = hr;
		}

		public Map<String, Object> getMap() {
			return map;
		}

		public void setMap(Map<String, Object> map) {
			this.map = map;
		}

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("{");
			sb.append("\"age\":")
					.append(age);
			sb.append(",\"name\":\"")
					.append(name).append('\"');
			sb.append(",\"hr\":\"")
					.append(hr).append('\"');
			sb.append(",\"map\":")
					.append(map);
			sb.append(",\"users\":")
					.append(users);
			sb.append('}');
			return sb.toString();
		}

		public static class User {

			private String name;

			private String hr;

			private String avg;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getHr() {
				return hr;
			}

			public void setHr(String hr) {
				this.hr = hr;
			}

			public String getAvg() {
				return avg;
			}

			public void setAvg(String avg) {
				this.avg = avg;
			}

			@Override
			public String toString() {
				final StringBuilder sb = new StringBuilder("{");
				sb.append("\"name\":\"")
						.append(name).append('\"');
				sb.append(",\"hr\":\"")
						.append(hr).append('\"');
				sb.append(",\"avg\":\"")
						.append(avg).append('\"');
				sb.append('}');
				return sb.toString();
			}
		}

	}

	@Component
	class SampleRunner implements ApplicationRunner {

		@Autowired
		private NacosConfigManager nacosConfigManager;

		@Override
		public void run(ApplicationArguments args) throws Exception {
			ConfigService configService = nacosConfigManager.getConfigService();
			configService.addListener(
					"nacos-config-custom.properties", "DEFAULT_GROUP", new Listener() {
						@Override
						public Executor getExecutor() {
							return null;
						}

						/**
						 * Callback with latest config data.
						 * @param configInfo latest config data for specific dataId in Nacos
						 * server
						 */
						@Override
						public void receiveConfigInfo(String configInfo) {
							Properties properties = new Properties();
							try {
								properties.load(new StringReader(configInfo));
							} catch (IOException e) {
								e.printStackTrace();
							}
							System.out.println("nacos-config-custom.properties config changed:" + properties);
						}
					}
			);

			// configService.addListener(
			// 		"test2.yaml", "GROUP_APP1", new Listener() {
			// 			@Override
			// 			public Executor getExecutor() {
			// 				return null;
			// 			}
			//
			// 			/**
			// 			 * Callback with latest config data.
			// 			 * @param configInfo latest config data for specific dataId in Nacos
			// 			 * server
			// 			 */
			// 			@Override
			// 			public void receiveConfigInfo(String configInfo) {
			// 				Properties properties = new Properties();
			// 				try {
			// 					properties.load(new StringReader(configInfo));
			// 				} catch (IOException e) {
			// 					e.printStackTrace();
			// 				}
			// 				System.out.println("test2.yaml config changed:" + properties);
			// 			}
			// 		}
			// );
		}
	}

	@RestController
	@RefreshScope
	class SampleController {
		@Autowired
		private UserConfig userConfig;

		@Autowired
		private NacosConfigManager nacosConfigManager;

		@Autowired
		private Environment environment;

		@Value("${user.name:zz}")
		String userName;

		@Value("${user.age:25}")
		Integer age;

		@GetMapping("/user")
		public String user() {
			return "Hello Nacos Config!\nValue Config [" + userName + " " + age + "] \n"
					+ "UserConfig [" + userConfig + "] \n"
					+ "NacosConfigManager [" + nacosConfigManager.getConfigService() + "]";
		}

		@GetMapping("/get/{name}")
		public String value(@PathVariable String name) {
			return "Environment config: " + String.valueOf(environment.getProperty(name));
		}

		@GetMapping("/bool")
		public String bool() {
			return "UserConfig map Config: " + (Boolean) userConfig.getMap().get("2");
		}
	}
}
