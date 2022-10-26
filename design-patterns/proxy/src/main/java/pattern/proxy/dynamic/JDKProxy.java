package pattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import pattern.proxy.TargetObject;
import pattern.proxy.TargetObjectImpl;

/**
 * JDK动态代理
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
/**
 * 关键点1：实现InvocationHandler的invoke方法（该方法中做增强，并调用目标对象原方法）
 */
public class JDKProxy implements InvocationHandler {

	/**
	 * 关键点2：持有目标对象实例
	 * 被代理对象(代理的目标对象)
	 */
	private Object target;

	public JDKProxy(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/**
		 * 关键点3：实现InvocationHandler的invoke方法里对目标对象做增强操作及调用目标对象原方法
		 */
		System.out.println(proxy.getClass());
		// 做一些事情
		System.out.println("proxy method before");
		Object invoke = method.invoke(this.target, args);
		// 做一些事情
		System.out.println("proxy method after");
		return invoke;
	}

	public static void main(String[] args) {
		// 设置生成代理类文件保存到本地，保存到了java-sample/com/sun/proxy目录下，名为$Proxy0
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
		/**
		 * 关键点4：通过Proxy.newProxyInstance()创建出代理类，传入目标类所在类加载器、目标对象接口、InvocationHandler实例
		 */
		TargetObject proxyInstance = (TargetObject) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), new Class[]{TargetObject.class}, new JDKProxy(new TargetObjectImpl()));
		String method = proxyInstance.method();
		System.out.println(method);
	}
}
