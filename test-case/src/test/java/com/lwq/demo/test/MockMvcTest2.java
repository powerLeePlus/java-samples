package com.lwq.demo.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.lwq.demo.test.controller.TestController;
import com.lwq.demo.test.service.TestService;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
//SpringBoot1.4版本之前用的是SpringJUnit4ClassRunner.class
@RunWith(SpringRunner.class)
/*@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc*/
// 注意只用@WebMvcTest需要测试类包路径在@SpringBootApplication标注的启动类包路径之下 或者加@ContextConfiguration注解
@WebMvcTest(TestController.class)
public class MockMvcTest2 {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TestService testService;

	@Before
	public void before() {
		// 打桩
		Mockito.when(testService.test1()).thenReturn("testService test1 mock数据");
	}

	@Test
	public void test1() throws Exception {
		/**
		 * 1、mockMvc.perform执行一个请求。
		 * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
		 * 3、ResultActions.param添加请求传值
		 * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
		 * 5、ResultActions.andExpect添加执行完成后的断言。
		 * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
		 *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
		 * 5、ResultActions.andReturn表示执行完成后返回相应的结果。
		 */
		// mock请求
		mockMvc.perform(MockMvcRequestBuilders.get("/test/1"))
				// 期待返回http status 200
				.andExpect(MockMvcResultMatchers.status().isOk())
				// 打印返回的response
				.andDo(MockMvcResultHandlers.print());
	}
}
