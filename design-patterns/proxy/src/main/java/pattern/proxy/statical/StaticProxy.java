package pattern.proxy.statical;

import pattern.proxy.TargetObject;
import pattern.proxy.TargetObjectImpl;

/**
 * 静态代理
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class StaticProxy implements TargetObject {
	// 代理模式和装饰者模式区别关键在这儿。目标对象是由代理对象生成的，这即所谓的控制对象的访问
	private TargetObject targetObject = new TargetObjectImpl();

	@Override
	public String method() {
		// 做一些事情
		System.out.println("proxy method before");
		String method = targetObject.method();
		// 做一些事情
		System.out.println("proxy method after");
		return method;
	}

	public static void main(String[] args) {
		StaticProxy staticProxy = new StaticProxy();
		String method = staticProxy.method();
		System.out.println(method);
	}
}
