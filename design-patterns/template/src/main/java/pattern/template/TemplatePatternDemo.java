package pattern.template;

/**
 * @author lwq
 * @date 2021/3/26 0026
 */
public class TemplatePatternDemo {

	public static void main(String[] args) {

		Game cricket = new Cricket();
		/** 4
		 * 直接调用模板方法
		 */
		cricket.play();

		System.out.println();

		Game football = new Football();
		/** 4
		 * 直接调用模板方法
		 */
		football.play();
	}
}
