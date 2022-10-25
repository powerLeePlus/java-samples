package pattern.adapater.sample2.adaptee;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class TFCardImpl implements TFCard {
	@Override
	public String readTF() {
		String msg ="tf card reade msg : hello word tf card";
		return msg;
	}
	@Override
	public int writeTF(String msg) {
		System.out.println("tf card write a msg : " + msg);
		return 1;
	}
}
