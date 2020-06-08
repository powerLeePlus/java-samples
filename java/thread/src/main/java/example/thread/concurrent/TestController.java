package example.thread.concurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 运维需求：检测服务是否可用;
 *
 * @author myh
 * @version 1.0
 * @Copyright:Copyright© 2018
 * @Company: www.duia.com
 * @date 2019年3月19日
 */
@RestController
@Slf4j
public class TestController {

	@Autowired
	private ThreadPoolExecutorDemo1 threadPoolExecutorDemo1;

	@GetMapping("/thread")
	public String testThread() {
		return threadPoolExecutorDemo1.test();
	}

}
