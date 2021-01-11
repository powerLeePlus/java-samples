import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
// @RunWith(BlockJUnit4ClassRunner.class) // 可以更改测试运行器 ，缺省值 org.junit.runner.Runner
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

	// 超时
	@Test(timeout = 1000)
	public void testTimeout() throws InterruptedException {
		TimeUnit.SECONDS.sleep(2);
		System.out.println("Complete");
	}

	// 忽略的测试方法（只在测试类的时候生效，单独执行该测试方法无效）
	@Ignore
	@Test
	public void testIgnore() {
		System.out.println("我被无视了？");
	}

	// 测试代码是否抛出了想要得到的异常
	@Test(expected = NullPointerException.class)
	public void testNPE() {
		throw new NullPointerException("空指针异常");
	}

	/**
	 * 参数化测试
	 * Junit 4 引入了一个新的功能参数化测试。参数化测试允许开发人员使用不同的值反复运行同一个测试。你将遵循 5 个步骤来创建参数化测试。
	 *
	 * 用 @RunWith(Parameterized.class)来注释 test 类。
	 * 创建一个由 @Parameters 注释的公共的静态方法，它返回 一个对象的集合(数组) 来作为测试数据集合。
	 * 创建一个公共的构造函数，它接受和一行测试数据相等同的东西。
	 * 为每一列测试数据创建一个实例变量。
	 * 用实例变量作为测试数据的来源来创建你的测试用例。
	 */
	//1.更改默认的测试运行器为RunWith(Parameterized.class)
	@RunWith(Parameterized.class)
	public static class ParameterTest {
		// 2.声明变量存放预期值和测试数据
		private String firstName;
		private String lastName;

		//3.声明一个返回值 为Collection的公共静态方法，并使用@Parameters进行修饰
		@Parameterized.Parameters //
		public static List<Object[]> param() {
			// 这里我给出两个测试用例
			return Arrays.asList(new Object[][]{{"Mike", "Black"}, {"Cilcln", "Smith"}});
		}

		//4.为测试类声明一个带有参数的公共构造函数，并在其中为之声明变量赋值
		public ParameterTest(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}
		// 5. 进行测试，发现它会将所有的测试用例测试一遍
		@Test
		public void test() {
			String name = firstName + " " + lastName;

			/**
			 * JUnit 4.4 结合 Hamcrest 提供了一个全新的断言语法——assertThat。
			 * 语法：
			 *  assertThat( [actual], [matcher expected] );
			 *
			 * assertThat 使用了 Hamcrest 的 Matcher 匹配符，用户可以使用匹配符规定的匹配准则精确的指定一些想设定满足的条件，具有很强的易读性，而且使用起来更加灵活。
			 */
			MatcherAssert.assertThat("Mike Black", Matchers.is(name));
		}
	}



}
