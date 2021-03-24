package pattern.adapater;

/**
 * Targer接口：客户访问的目标
 * @author lwq
 * @date 2021/3/24 0024
 */
public interface MediaPlayer {

	/** 1
	 * 客户希望通过访问该方法能播放mp3,mp4,vlc。没有适配前只能播mp3
	 */
	void play(String audioType, String fileName);
}
