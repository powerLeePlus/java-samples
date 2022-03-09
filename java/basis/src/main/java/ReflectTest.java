import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author lwq
 * @date 2022/3/9 0009
 * @since
 */
public class ReflectTest {

	/**
	 * 获取Class对象的3种方式
	 */
	public static Class getClazz() throws ClassNotFoundException {
		// 1
		// Person person = new Person();
		// Class<? extends Person> personClass = person.getClass();
		// 2
		// Class<Person> personClass = Person.class;
		// 3 使用Class类中的forName()静态方法(最安全/性能最好)
		Class<?> personClass = Class.forName("ReflectTest$Person");
		return personClass;
	}

	/**
	 * 反射创建对象的两种方法
	 */
	public static Person getInstance(Class<Person> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		// 1
		// Person person = clazz.newInstance();
		// 2
		Constructor<Person> constructor = clazz.getConstructor(String.class, Integer.class, Integer.class);
		Person person = constructor.newInstance("张三", 1, 20);
		System.out.println(person);
		return person;
	}

	public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		// 获取Class对象
		Class clazz = getClazz();
		System.out.println(clazz.getName());

		// 获取 Person 类的所有方法信息
		Method[] declaredMethods = clazz.getDeclaredMethods();
		System.out.println(Arrays.toString(declaredMethods));
		// 获取 Person 类的所有成员属性信息
		Field[] declaredFields = clazz.getDeclaredFields();
		System.out.println(Arrays.toString(declaredFields));
		// 获取 Person 类的所有构造方法信息
		Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
		System.out.println(Arrays.toString(declaredConstructors));

		// 通过反射创建对象
		Person instance = getInstance(clazz);
	}

	public static class Person {
		private String name;
		private Integer gender;
		private Integer age;

		public Person() {
		}

		public Person(String name, Integer gender, Integer age) {
			this.name = name;
			this.gender = gender;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getGender() {
			return gender;
		}

		public void setGender(Integer gender) {
			this.gender = gender;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("{");
			sb.append("\"name\":\"")
					.append(name).append('\"');
			sb.append(",\"gender\":")
					.append(gender);
			sb.append(",\"age\":")
					.append(age);
			sb.append('}');
			return sb.toString();
		}
	}
}
