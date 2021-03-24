package pattern.adapater;

/** Adapatee：被适配者，适配前已经存在
 * @author lwq
 * @date 2021/3/24 0024
 */
public interface AdvancedMediaPlayer {

	/** 2
	 * 能播mp4和vlc的播放器，需要被适配到mp3播放器
	 */
	public void playMp4(String fileName);

	public void playVlc(String fileName);
}
