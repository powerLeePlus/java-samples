package pattern.adapater;

/**
 * Target实现：适配前已经存在
 * @author lwq
 * @date 2021/3/24 0024
 */
public class AudioPlayer implements MediaPlayer {

	private MediaAdapater mediaAdapater;

	/** 2
	 * 适配前只能播放mp3
	 */
	// @Override
	// public void play(String audioType, String fileName) {
	// 	if (audioType == null) {
	// 		System.out.println("Invalid media. audioType is null");
	// 	}
	//
	// 	if (audioType.equalsIgnoreCase("mp3")) {
	// 		System.out.println("Playing mp3 file. Name: "+ fileName);
	// 	}
	//
	// 	else {
	// 		System.out.println("Invalid media. " + audioType + " format not supported");
	// 	}
	// }

	/** 6
	 * 适配后mp3，mp4，vlc都能播放
	 */
	@Override
	public void play(String audioType, String fileName) {
		if (audioType == null) {
			System.out.println("Invalid media. audioType is null");
		}

		if (audioType.equalsIgnoreCase("mp3")) {
			/** 7
			 * 原来的mp3播放功能
			 */
			System.out.println("Playing mp3 file. Name: "+ fileName);
		} else if (audioType.equalsIgnoreCase("mp4") || audioType.equalsIgnoreCase("vlc")) {
			/** 7
			 * 通过适配获得的mp4/vlc播放功能
			 */
			mediaAdapater = new MediaAdapater(audioType.equalsIgnoreCase("mp4") ? new Mp4Player() : new VlcPlayer());
			mediaAdapater.play(audioType, fileName);
		} else {
			System.out.println("Invalid media. " + audioType + " format not supported");
		}
	}
}
