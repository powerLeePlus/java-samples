package pattern.adapater.sample2.targetinterface;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class XiaomiComputer implements Computer {
	@Override
	public String readSD(SDCard sdCard) {
		if (sdCard == null) {
			throw new NullPointerException("SDCard is null");
		}
		return sdCard.readSD();
	}
}
