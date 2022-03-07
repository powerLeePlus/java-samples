import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author lwq
 * @date 2022/3/7 0007
 * @since
 */
public class ClassLoaderTest {

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

	public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
		MyClassLoader myClassLoader = new MyClassLoader();
		Class<?> clazz = Class.forName("People", true, myClassLoader);
		Object o = clazz.newInstance();
		System.out.println(o);
		// 打印出我们的自定义类加载器
		System.out.println(o.getClass().getClassLoader());
	}

}
