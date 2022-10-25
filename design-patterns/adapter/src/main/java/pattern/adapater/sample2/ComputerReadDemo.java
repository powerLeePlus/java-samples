package pattern.adapater.sample2;

import pattern.adapater.sample2.adaptee.TFCard;
import pattern.adapater.sample2.adaptee.TFCardImpl;
import pattern.adapater.sample2.adapter.SDAdapterTF;
import pattern.adapater.sample2.target.Computer;
import pattern.adapater.sample2.target.SDCard;
import pattern.adapater.sample2.target.SDCardImpl;
import pattern.adapater.sample2.target.XiaomiComputer;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class ComputerReadDemo {

	public static void main(String[] args) {
		String s1 = beforeAdapte();
		System.out.println(s1);

		System.out.println("------------------------------");

		String s2 = afterAdapte();
		System.out.println(s2);
	}

	public static String beforeAdapte() {
		Computer computer = new XiaomiComputer();
		SDCard sdCard = new SDCardImpl();
		return computer.readSD(sdCard);
	}

	public static String afterAdapte() {
		Computer computer = new XiaomiComputer();
		TFCard tfCard = new TFCardImpl();
		SDCard sdCard = new SDAdapterTF(tfCard);
		return computer.readSD(sdCard);
	}
}
