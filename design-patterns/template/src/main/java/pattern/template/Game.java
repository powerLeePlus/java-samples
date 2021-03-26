package pattern.template;

/**
 * 公共抽象父类
 * @author lwq
 * @date 2021/3/26 0026
 */
public abstract class Game {

	/** 2
	 * 都需要的操作，定义好，由子类实现
	 */
	abstract void initialize();
	abstract void startPlay();
	abstract void endPlay();

	/** 1
	 * 模板方法，一些具体的处理只定义由具体子类实现
	 */
	public final void play() {
		initialize();

		startPlay();

		endPlay();
	}
}
