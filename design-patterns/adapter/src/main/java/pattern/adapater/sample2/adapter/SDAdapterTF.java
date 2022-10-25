package pattern.adapater.sample2.adapter;

import pattern.adapater.sample2.adaptee.TFCard;
import pattern.adapater.sample2.targetinterface.SDCard;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class SDAdapterTF implements SDCard {
	private TFCard tfCard;

	public SDAdapterTF(TFCard tfCard) {
		this.tfCard = tfCard;
	}

	@Override
	public String readSD() {
		System.out.println("adapter read tf card ");
		return tfCard.readTF();
	}

	@Override
	public int writeSD(String msg) {
		System.out.println("adapter write tf card");
		return tfCard.writeTF(msg);
	}
}
