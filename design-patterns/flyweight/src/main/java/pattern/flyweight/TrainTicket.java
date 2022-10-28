package pattern.flyweight;

import java.util.Random;

/**
 * 具体享元角色 火车票
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class TrainTicket implements Ticket {
	private String from;
	private String to;
	private int price;

	public TrainTicket(String from, String to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public void showInfo(String bunk) {
		this.price = new Random().nextInt(500);
		System.out.println(String.format("%s->%s：%s，价格：%s元", this.from, this.to, bunk, this.price));	}
}
