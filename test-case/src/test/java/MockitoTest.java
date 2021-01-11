import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
public class MockitoTest {

	/**
	 * 创建mock对象
	 */
	@Test
	public void testCreateMockObject() {
		// 使用mock静态方法创建mock对象
		Map mock = Mockito.mock(Map.class);
		Assert.assertTrue("类型错误", mock instanceof Map);
		// Assert.isInstanceOf(List.class, mock, "类型错误");

		// 不仅可以mock接口，还可以mock类
		HashMap mock1 = Mockito.mock(HashMap.class);
		Assert.assertTrue(mock1 instanceof Map);
		Assert.assertTrue(mock1 instanceof HashMap);
		// Assert.assertTrue("类型错误", mock1 instanceof TreeMap);

	}

	/**
	 * 配置mock对象行为
	 */
	@Test
	public void testConfigMock() {
		// 使用mock静态方法创建mock对象
		Map map = Mockito.mock(Map.class);

		// 当调用map.size()时，返回100
		Mockito.when(map.size()).thenReturn(100);
		Assert.assertTrue("size不等于100", map.size() == 100);

		// 当调用map.put()时，返回true
		Mockito.when(map.put(1, 1)).thenReturn(true);
		Assert.assertNotNull("没有put成功", map.put(1, 1));

		// 当调用map.get(2)时，抛出NPE
		// Mockito.when(map.get(2)).thenThrow(new NullPointerException());
		// Mockito.doThrow(new NullPointerException()).when(map).get(2);
		map.get(2);

		// 当调用map.remove()时，什么都不做。
		Mockito.doNothing().when(map).clear();
		map.clear();

		// Answer 是个泛型接口。到调用发生时将执行这个回调，可以进行一些处理，而不是简单的return或throw
		// 通过  Object[] args = invocation.getArguments();
		// 可以拿到调用时传入的参数，通过 Object mock = invocation.getMock();可以拿到mock对象。
		Mockito.when(map.isEmpty()).thenAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return true;
			}
		});
		// Assert.assertEquals("不和预期", false, map.isEmpty());
		Assert.assertEquals("不符预期", true, map.isEmpty());

		// verify检测方法调用，如下表示size()方法调用一次
		Mockito.verify(map, Mockito.times(1)).size();

	}

	/**
	 * spy 部分模拟
	 */
	@Test
	public void testSpy() {

		List list = new LinkedList();

		List spy = Mockito.spy(list);
		// 对spy.size()进行定制
		Mockito.when(spy.size()).thenReturn(100);

		spy.add("one");
		spy.add("two");

		// 因为没有对spy.get(0)和spy.get(1)进行定制，所以是真实的调用
		Assert.assertEquals("one", spy.get(0));
		Assert.assertEquals("two", spy.get(1));

		// 因为对spy.size()进行了定制，所以是模型的数据
		Assert.assertTrue(spy.size() == 100);

	}

	/**
	 * 一个方法调用过程中，传递的参数也可以被捕获
	 */
	@Test
	public void testCaptureArgument() {
		// 字符串参数
		String param = "test";
		// mock一个list
		List mockObject = Mockito.mock(List.class);
		// 捕获参数的对象
		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		// mock对象执行add()，将test添加进去
		mockObject.add(param);
		// 捕获参数
		Mockito.verify(mockObject).add(argumentCaptor.capture());

		Assert.assertTrue("test".equals(argumentCaptor.getValue()));

	}
}
