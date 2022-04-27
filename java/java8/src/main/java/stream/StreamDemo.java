package stream;

import java.util.Arrays;
import java.util.List;

/**
 * 常用的stream用法
 * @author lwq
 * @date 2022/4/27 0027
 * @since
 */
public class StreamDemo {
	public static List<String> strings = Arrays.asList("2", "3", "1");

	public static void main(String[] args) {
		forEach();
	}
	
	/**
	 * foreach
	 */
	private static void forEach() {
		strings.stream().forEach(System.out::println);
	}

}
