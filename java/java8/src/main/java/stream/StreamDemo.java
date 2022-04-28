package stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 常用的stream用法
 * @author lwq
 * @date 2022/4/27 0027
 * @since
 */
public class StreamDemo {
	public static List<String> strings = Arrays.asList("2", "3", "1", "2");

	public static void main(String[] args) {
		// forEach();
		// collect();
		// filter();
		// map();
		// mapToInt();
		// distinct();
		// sorted();
		// groupbingBy();
		// findFirst();
		// anyMatch();
		// reduce();
		// peek();
		// limit();
		max();
	}
	
	/**
	 * 1、foreach
	 */
	private static void forEach() {
		strings.stream().forEach(System.out::println);
	}
	
	/**
	 * 2、collect
	 */
	private static void collect() {
		List<Integer> collect = strings.stream().map(Integer::valueOf).collect(Collectors.toList());
		forEach(collect);
	}

	/**
	 * 3、filter
	 */
	private static void filter() {
		List<String> collect = strings.stream().filter(s -> s.equals("1")).collect(Collectors.toList());
		forEach(collect);
	}
	
	/**
	 * 4、map
	 */
	private static void map() {
		List<String> collect = strings.stream().map(s -> "s:" + s).collect(Collectors.toList());
		forEach(collect);
	}
	
	/**
	 * 5、mapToInt mapToDouble...
	 */
	private static void mapToInt() {
		List<Integer> collect = strings.stream().mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
		forEach(collect);
	}
	
	/**
	 * 6、distinct
	 */
	private static void distinct() {
		List<Integer> collect = strings.stream().mapToInt(Integer::valueOf).distinct().boxed().collect(Collectors.toList());
		forEach(collect);
	}

	/**
	 * 7、sorted
	 */
	private static void sorted() {
		List<Integer> collect = strings.stream().map(Integer::valueOf).sorted().collect(Collectors.toList());
		forEach(collect);
	}
	
	/**
	 * 8、groupingBy
	 */
	private static void groupbingBy() {
		Map<Object, Long> collect = strings.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
		System.out.println(collect);
	}

	/**
	 * 9、findFirst，findAny
	 */
	private static void findFirst() {
		Optional<String> first = strings.stream().filter(s -> s.equals("2")).findFirst();
		System.out.println(first.get());

	}

	/**
	 * 10、nyMatch，allMatch，noneMatch
	 */
	private static void anyMatch() {
		boolean anyMatch = strings.stream().anyMatch(s -> s.equals("2"));
		System.out.println(anyMatch);
	}

	/**
	 * 11、reduce
	 */
	private static void reduce() {
		// s1 和 s2 表示循环中的前后两个数
		String s = strings.stream().reduce((s1, s2) -> s1 + s2).get();
		System.out.println(s);
		// 第一个参数表示基数，会从 100 开始加
		Integer integer = strings.stream().map(Integer::valueOf).reduce(100, (s1, s2) -> s1 + s2);
		System.out.println(integer);
	}

	/**
	 * 12、peek
	 */
	private static void peek() {
		// 我们在 peek 方法里面做任意没有返回值的事情，比如打印日志
		String collect = strings.stream().peek(System.out::println).collect(Collectors.joining());
		System.out.println(collect);
	}

	/**
	 * 13、limit
	 */
	private static void limit() {
		List<String> collect = strings.stream().sorted().limit(2).collect(Collectors.toList());
		forEach(collect);
	}

	/**
	 * 14、max，min
	 */
	private static void max() {
		Optional<String> max = strings.stream().max(Comparator.comparing(Integer::valueOf));
		System.out.println(max.orElse("null"));

		Optional<String> min = strings.stream().min(Comparator.comparing(Integer::valueOf));
		System.out.println(min.orElse("null"));
	}

	private static void forEach(List stringList) {
		stringList.stream().forEach(System.out::println);
	}
}
