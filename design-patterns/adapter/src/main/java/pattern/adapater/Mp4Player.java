package pattern.adapater;

/**
 * Adapatee具体实现一：mp4播放器
 * @author lwq
 * @date 2021/3/24 0024
 */
public class Mp4Player implements AdvancedMediaPlayer {
	@Override
	public void playMp4(String fileName) {
		System.out.println("Playing mp4 file. Name: " + fileName);
	}

	@Override
	public void playVlc(String fileName) {

	}
}
