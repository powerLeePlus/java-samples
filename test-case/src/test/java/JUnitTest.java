import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.*;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
public class JUnitTest {

	protected StopWatch stopWatch;

	@BeforeClass
	public static void beforeClass() {
		System.out.println("执行BeforeClass");
	}

	@Before
	public void before() {
		System.out.println("执行before");
		stopWatch = StopWatch.createStarted();
	}

	@After
	public void after() {
		stopWatch.stop();
		System.out.println("执行after，用时：" + stopWatch.getTime() + "ms");
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("执行afterClass");
	}
	
	@Test
	public void test1() throws InterruptedException {
		System.out.println("执行test1");
		TimeUnit.SECONDS.sleep(1);
	}

}
