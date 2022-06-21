package nio;

import java.nio.ByteBuffer;

/**
 * nio 直接内存
 * @author lwq
 * @date 2022/6/1 0001
 * @since
 */
public class DirectMemoryTest {

	public static void main(String[] args) {
		allocateCompare();
		operateCompare();
	}

	/**
	 * 从数据流的角度看
	 * 非直接内存作用链：
	 *  本地IO -> 直接内存 -> 非直接内存 -> 直接内存 -> 本地IO
	 * 直接内存作用链：
	 * 	本地IO -> 直接内存 -> 本地IO
	 *
	 * 直接内存使用场景
	 * -有很大的数据需要存储，它的生命周期很长
	 * -适合频繁的IO操作，例如网络并发场景
	 */

	/**
	 * 直接内存和堆内存的分配空间比较
	 * 结论：在数据量提升时，直接内存相比非直接内存的申请，有很严重的性能问题
	 */
	public static void allocateCompare() {
		int time = 10000000; //操作次数
		long st = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			// ByteBuffer.allocate(int capacity)  分配一个新的字节缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(2); // 非直接内存分配申请
		}
		long et = System.currentTimeMillis();
		System.out.println("分配操作次数：" + time + "，堆内存分配耗时：" + (et - st) + "ms");

		long st_direct = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			// ByteBuffer.allocateDirect(int capacity) 分配新的直接内存字节缓冲区
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2); // 直接内存分配申请
		}
		long et_direct = System.currentTimeMillis();
		System.out.println("分配操作次数：" + time + "，直接内存分配耗时：" + (et_direct - st_direct) + "ms");
	}

	/**
	 * 直接内存和堆内存的读写性能比较
	 * 结论：直接内存在直接的IO操作上，在频繁的读写时会有显著的性能提升
	 */
	public static void operateCompare() {
		int time = 100000000;
		ByteBuffer buffer = ByteBuffer.allocate(2 * time);
		long st = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			// buffer.putChar(char value) 用来写入char值的相对put方法
			buffer.putChar('a');
		}
		buffer.flip();
		for (int i = 0; i < time; i++) {
			buffer.getChar();
		}
		long et = System.currentTimeMillis();
		System.out.println("读写操作次数：" + time + "，非直接内存读写耗时：" + (et - st) + "ms");

		ByteBuffer buffer_direct = ByteBuffer.allocateDirect(2 * time);
		long st_direct = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			buffer_direct.putChar('a');
		}
		buffer_direct.flip();
		for (int i = 0; i < time; i++) {
			buffer_direct.getChar();
		}
		long et_direct = System.currentTimeMillis();
		System.out.println("读写操作次数：" + time + "，直接内存读写耗时：" + (et_direct - st_direct) + "ms");
	}
}
