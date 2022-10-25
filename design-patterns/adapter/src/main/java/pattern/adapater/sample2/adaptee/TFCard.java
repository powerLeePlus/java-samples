package pattern.adapater.sample2.adaptee;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public interface TFCard {

	/**
	 * 读取TF卡功能
	 */
	String readTF();

	/**
	 * 写TF卡功能
	 */
	int writeTF(String msg);
}
