package pattern.adapater.sample2.targetinterface;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public interface SDCard {

	/**
	 * 读取SD卡功能
	 */
	String readSD();

	/**
	 * 写SD卡功能
	 */
	int writeSD(String msg);
}
