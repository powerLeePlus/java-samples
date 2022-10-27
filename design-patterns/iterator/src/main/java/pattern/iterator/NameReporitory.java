package pattern.iterator;

/**
 * 被迭代对象-具体实现对象
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class NameReporitory implements Container {
	public String[] names = {"关羽", "张飞", "赵云"};

	@Override
	public Iterator getIterator() {
		return new NameIterator();
	}

	/**
	 * 迭代器-具体实现
	 * @author lwq
	 * @date 2022/10/27 0027
	 * @since
	 */
	private class NameIterator implements Iterator {
		int index;

		@Override
		public boolean hasNext() {
			if (index < names.length) {
				return true;
			}
			return false;
		}

		@Override
		public Object next() {
			if (this.hasNext()) {
				return names[index++];
			}
			return null;
		}
	}

	public static void main(String[] args) {
		NameReporitory nameReporitory = new NameReporitory();
		Iterator iterator = nameReporitory.getIterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			System.out.println(next.toString());
		}
	}
}
