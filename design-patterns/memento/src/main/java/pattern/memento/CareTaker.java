package pattern.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责人：管理备忘录的创建与读取
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class CareTaker extends Originator {
	private List<Memento> mementos = new ArrayList<>();

	public void add(Memento memento) {
		mementos.add(memento);
	}

	public Memento get(int index) {
		return mementos.get(index);
	}
}
