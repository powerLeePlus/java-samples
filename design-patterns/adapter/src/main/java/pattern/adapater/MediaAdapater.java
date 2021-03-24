package pattern.adapater;

/**
 * Adapter：适配者，为了适配而新增的
 * @author lwq
 * @date 2021/3/24 0024
 */
public class MediaAdapater implements MediaPlayer {

	/** 3
	 * 适配器要实现Target
	 */
	/** 4
	 * 适配器要持有被适配者的引用或继承被适配者（这里是前者）
	 */
	AdvancedMediaPlayer advancedMediaPlayer;

	public MediaAdapater(AdvancedMediaPlayer advancedMediaPlayer) {
		this.advancedMediaPlayer = advancedMediaPlayer;
	}

	/** 5
	 * 调用被适配者的方法
	 */
	@Override
	public void play(String audioType, String fileName) {
		if (audioType == null) {
			System.out.println("Invalid media. audioType is null");
		}

		if (audioType.equalsIgnoreCase("mp4")) {
			advancedMediaPlayer.playMp4(fileName);
		} else if (audioType.equalsIgnoreCase("vlc")) {
			advancedMediaPlayer.playVlc(fileName);
		}
	}
}
