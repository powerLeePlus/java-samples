import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lwq.demo.TestApplication;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class SpringbootTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void test() {
		System.out.println(applicationContext.getDisplayName());
	}
}
