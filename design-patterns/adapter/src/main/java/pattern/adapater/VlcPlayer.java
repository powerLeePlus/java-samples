package pattern.adapater;

/**
 * Adapatee具体实现一：mp4播放器
 * @author lwq
 * @date 2021/3/24 0024
 */
public class VlcPlayer implements AdvancedMediaPlayer {
	@Override
	public void playMp4(String fileName) {
	}

	@Override
	public void playVlc(String fileName) {
		System.out.println("Playing vlc file. Name: " + fileName);
	}
}
