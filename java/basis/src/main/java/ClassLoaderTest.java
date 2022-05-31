import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;

/**
 * @author lwq
 * @date 2022/3/7 0007
 * @since
 */
public class ClassLoaderTest {

	/**
	 * 自定义类加载器
	 * 自定义类加载需要实现findClass方法。loadClass方法会调用该方法
	 *
	 * 虽然在绝大多数情况下，系统默认提供的类加载器实现已经可以满足需求。但是在某些情况下，您还
	 * 是需要为应用开发出自己的类加载器。比如您的应用通过网络来传输 Java
	 * 类的字节代码，为了保证安全性，这些字节代码经过了加密处理。这个时候您就需要自己的类加载器来
	 * 从某个网络地址上读取加密后的字节代码，接着进行解密和验证，最后定义出要在 Java 虚拟机中运行的
	 * 类来。
	 */
	static class MyClassLoader extends ClassLoader {

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			File file = new File("E:\\java\\study\\demos\\java\\basis\\src\\main\\java\\People.class");
			try {
				// byte[] classByte = getClassByte(file);
				byte[] classByte = getClassByte2(file);
				/**
				 * 灵感，可以用classByte.length计算一个类乃至一个对象的内存大小
				 */
				//defineClass方法可以把二进制流字节组成的文件转换为一个java.lang.Class
				Class<?> c = this.defineClass(name, classByte, 0, classByte.length);
				return c;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return super.findClass(name);
		}

		// io方式
		private byte[] getClassByte(File file) throws IOException {
			FileInputStream fileInputStream = new FileInputStream(file);
			// 这里要读入.class的字节，因此要使用字节流
			ByteArrayOutputStream byteArrayOutputStream = null;
			try {
				// byte[] bytes = new byte[1]; // 设置的需要小一些否则会报：java.lang.ClassFormatError: Extra bytes at the end of class file People
				byteArrayOutputStream = new ByteArrayOutputStream();
				int len;
				// while (fileInputStream.read(bytes) != -1) {
				while ((len = fileInputStream.read()) != -1) {
					// byteArrayOutputStream.write(bytes);
					// 一个字节一个字节的写
					byteArrayOutputStream.write(len);
				}
				return byteArrayOutputStream.toByteArray();
			} finally {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
		}

		// nio方式
		private byte[] getClassByte2(File file) throws IOException {
			// 这里要读入.class的字节，因此要使用字节流
			FileInputStream fileInputStream = new FileInputStream(file);
			FileChannel fc = fileInputStream.getChannel();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			WritableByteChannel writableByteChannel = Channels.newChannel(byteArrayOutputStream);
			ByteBuffer allocate = ByteBuffer.allocate(1024);
			while (true) {
				int read = fc.read(allocate);
				if (read ==0 || read == -1) {
					break;
				}
				allocate.flip();
				writableByteChannel.write(allocate);
				allocate.clear();
			}
			writableByteChannel.close();
			byteArrayOutputStream.close();
			fc.close();
			fileInputStream.close();
			return byteArrayOutputStream.toByteArray();
		}
	}

	static class MyClassLoader2 extends ClassLoader {

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			File file = new File("E:\\java\\study\\demos\\java\\basis\\src\\main\\java\\People.class");
			try {
				// byte[] classByte = getClassByte(file);
				byte[] classByte = getClassByte2(file);
				/**
				 * 灵感，可以用classByte.length计算一个类乃至一个对象的内存大小
				 */
				//defineClass方法可以把二进制流字节组成的文件转换为一个java.lang.Class
				Class<?> c = this.defineClass(name, classByte, 0, classByte.length);
				return c;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return super.findClass(name);
		}

		// io方式
		private byte[] getClassByte(File file) throws IOException {
			FileInputStream fileInputStream = new FileInputStream(file);
			// 这里要读入.class的字节，因此要使用字节流
			ByteArrayOutputStream byteArrayOutputStream = null;
			try {
				// byte[] bytes = new byte[1]; // 设置的需要小一些否则会报：java.lang.ClassFormatError: Extra bytes at the end of class file People
				byteArrayOutputStream = new ByteArrayOutputStream();
				int len;
				// while (fileInputStream.read(bytes) != -1) {
				while ((len = fileInputStream.read()) != -1) {
					// byteArrayOutputStream.write(bytes);
					// 一个字节一个字节的写
					byteArrayOutputStream.write(len);
				}
				return byteArrayOutputStream.toByteArray();
			} finally {
				if (byteArrayOutputStream != null) {
					byteArrayOutputStream.close();
				}
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
		}

		// nio方式
		private byte[] getClassByte2(File file) throws IOException {
			// 这里要读入.class的字节，因此要使用字节流
			FileInputStream fileInputStream = new FileInputStream(file);
			FileChannel fc = fileInputStream.getChannel();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			WritableByteChannel writableByteChannel = Channels.newChannel(byteArrayOutputStream);
			ByteBuffer allocate = ByteBuffer.allocate(1024);
			while (true) {
				int read = fc.read(allocate);
				if (read ==0 || read == -1) {
					break;
				}
				allocate.flip();
				writableByteChannel.write(allocate);
				allocate.clear();
			}
			writableByteChannel.close();
			byteArrayOutputStream.close();
			fc.close();
			fileInputStream.close();
			return byteArrayOutputStream.toByteArray();
		}
	}

	public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		// MyClassLoader myClassLoader = new MyClassLoader();
		// Class<?> clazz = Class.forName("People", true, myClassLoader);
		// Object o = clazz.newInstance();
		// System.out.println(o);
		// // 打印出我们的自定义类加载器
		// System.out.println(o.getClass().getClassLoader());

		// showClassLoaderTree();

		diffLoader();
	}

	/**
	 * 显示类加载器的树状结构
	 */
	public static void showClassLoaderTree() {
		ClassLoader classLoader = MyClassLoader.class.getClassLoader();
		while (classLoader != null) {
			System.out.println(classLoader.getClass().getName());
			classLoader = classLoader.getParent();
		}
		// 最后当loader=null的时候。这个时候loader代表的是引导类加载器BootStrap
		System.out.println(classLoader);
	}
	
	/**
	 * Java 虚拟机是如何判定两个Java类是相同的。
	 * Java虚拟机不仅要看类的全名是否相同，还要看加载此类的类加载器是否一样。
	 * 只有两者都相同的情况，才认为两个类是相同的。即便是同样的字节代码，被不同的类加载器加载之后所得到的类，也是不同的。
	 */
	public static void diffLoader() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		MyClassLoader myClassLoader = new MyClassLoader();
		Class<?> clazz1 = myClassLoader.loadClass("People");
		MyClassLoader2 myClassLoader2 = new MyClassLoader2();
		Class<?> clazz2 = myClassLoader2.loadClass("People");
		//这是由不同的类加载器加载的类生成的对象
		Object obj1 = clazz1.newInstance();
		Object obj2 = clazz2.newInstance();

		//这是Date对象，是由同一个类加载器加载的
		Date date1 = new Date();
		Date date2 = new Date();

		//比较他们是否是同一个Class类的实例
		System.out.println(obj1.getClass().getName() + "-" + obj2.getClass().getName() + "-" + obj1.getClass().equals(obj2.getClass()));
		System.out.println(date1.getClass().equals(date2.getClass()));
	}

}
