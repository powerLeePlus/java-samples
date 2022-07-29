package demo.lwq.spring.boot.starter.customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwq
 * @date 2022/7/29 0029
 * @since
 */
@RestController
public class CalculateController {
	@Autowired
	private AddService addService;
	@Autowired
	private MinusService minusService;

	@GetMapping("/add/{added}/{add}")
	public String add(@PathVariable int added, @PathVariable int add) {
		return added + "加" + add + "等于：" + addService.add(added, add);
	}

	@GetMapping("/minus/{minuend}/{substraction}")
	public String minus(@PathVariable int minuend, @PathVariable int substraction) throws MinusException {
		return  minuend + "减" + substraction + "等于：" + minusService.minus(minuend, substraction);
	}
}
