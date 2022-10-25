package pattern.adapater.sample2.targetinterface;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class SDCardImpl implements SDCard {
	@Override
	public String readSD() {
		String msg = "sdcard read a msg :hello word SD";
		return msg;
	}

	@Override
	public int writeSD(String msg) {
		System.out.println("sd card write msg : " + msg);
		return 1;
	}
}
