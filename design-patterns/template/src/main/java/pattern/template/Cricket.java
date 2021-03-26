package pattern.template;

/**
 * 具体实现类一
 * @author lwq
 * @date 2021/3/26 0026
 */
public class Cricket extends Game {
	/** 3
	 * 某些处理子类自己实现
	 */
	@Override
	void initialize() {
		System.out.println("Cricket Game Initialized! Start playing.");
	}

	@Override
	void startPlay() {
		System.out.println("Cricket Game Started. Enjoy the game!");
	}

	@Override
	void endPlay() {
		System.out.println("Cricket Game Finished!");
	}
}
