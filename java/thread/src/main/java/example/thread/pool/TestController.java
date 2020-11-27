package example.thread.pool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
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
