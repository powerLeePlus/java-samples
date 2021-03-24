package pattern.adapater;

/**
 * @author lwq
 * @date 2021/3/24 0024
 */
public class AdapaterPatternDemo {

	public static void main(String[] args) {
		// beforeAdapated();
		// adpated1();
		adpated2();
	}


	/** 3
	 * 适配前，只能播mp3
	 */
	public static void beforeAdapated() {
		MediaPlayer player = new AudioPlayer();
		player.play("mp3", "beyond the horizon.mp3");
		player.play("mp4", "alone.mp4");
		player.play("vlc", "far far away.vlc");
		player.play("avi", "mind me.avi");
	}

	/** 6
	 * 直接使用适配器访问
	 */
	public static void adpated1() {
		MediaPlayer mp4player = new MediaAdapater(new Mp4Player());
		mp4player.play("mp3", "beyond the horizon.mp3");
		mp4player.play("mp4", "alone.mp4");
		mp4player.play("vlc", "far far away.vlc");
		mp4player.play("avi", "mind me.avi");

		MediaPlayer vlcPlayer = new MediaAdapater(new VlcPlayer());
		vlcPlayer.play("mp3", "beyond the horizon.mp3");
		vlcPlayer.play("mp4", "alone.mp4");
		vlcPlayer.play("vlc", "far far away.vlc");
		vlcPlayer.play("avi", "mind me.avi");
	}

	/** 8
	 * 通过Target访问
	 */
	public static void adpated2() {

		MediaPlayer player = new AudioPlayer();
		player.play("mp3", "beyond the horizon.mp3");
		player.play("mp4", "alone.mp4");
		player.play("vlc", "far far away.vlc");
		player.play("avi", "mind me.avi");
	}
}
