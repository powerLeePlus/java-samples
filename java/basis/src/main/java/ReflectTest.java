import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.management.MXBean;

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

		/**
		 * getDeclared...   获取所有的属性、方法、构造器
		 * get...           获取所有的public属性、方法、构造器
		 */
		// 获取 Person 类的所有方法信息
		Method[] declaredMethods = clazz.getDeclaredMethods();
		System.out.println(Arrays.toString(declaredMethods));
		// 获取 Person 类的所有成员属性信息
		Field[] declaredFields = clazz.getDeclaredFields();
		System.out.println(Arrays.toString(declaredFields));
		Field[] fields = clazz.getFields();
		System.out.println(Arrays.toString(fields));
		// 获取 Person 类的所有构造方法信息
		Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
		System.out.println(Arrays.toString(declaredConstructors));
		// 获取 Person 类的所有annotation信息
		Annotation[] annotations = clazz.getAnnotations();
		System.out.println(Arrays.toString(annotations));

		// 通过反射创建对象
		Person instance = getInstance(clazz);
	}

	@MXBean
	public static class Person {
		private String name;
		private Integer gender;
		private Integer age;

		String addr;
		protected String education;
		public String nickname;

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

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public String getEducation() {
			return education;
		}

		public void setEducation(String education) {
			this.education = education;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
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
			sb.append(",\"addr\":\"")
					.append(addr).append('\"');
			sb.append(",\"education\":\"")
					.append(education).append('\"');
			sb.append(",\"nickname\":\"")
					.append(nickname).append('\"');
			sb.append('}');
			return sb.toString();
		}
	}
}
