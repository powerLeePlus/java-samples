package pattern.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂  12306
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class TicketFactory {
	private static Map<String,Ticket> ticketMap = new HashMap<>();

	public static Ticket queryTicket(String from, String to) {
		String key = from + "->" + to;
		if (ticketMap.containsKey(key)) {
			System.out.println("使用缓存：" + key);
			return ticketMap.get(key);
		}
		System.out.println("首次查询，创建并放入缓存：" + key);
		Ticket ticket = new TrainTicket(from, to);
		ticketMap.put(key, ticket);
		return ticket;
	}

	public static void main(String[] args) {
		Ticket ticket1 = TicketFactory.queryTicket("北京", "上海");
		ticket1.showInfo("硬座");
		Ticket ticket2 = TicketFactory.queryTicket("北京", "上海");
		ticket2.showInfo("硬座");
		ticket2.showInfo("软卧");

	}
}
