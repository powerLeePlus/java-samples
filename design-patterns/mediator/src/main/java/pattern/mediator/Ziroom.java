package pattern.mediator;

/**
 * 具体中介-自如
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Ziroom extends Mediator {

	// 中介必须知道所有房主和租客信息
	private HouseOwner houseOwner;
	private Tenant tenant;

	public HouseOwner getHouseOwner() {
		return houseOwner;
	}

	public void setHouseOwner(HouseOwner houseOwner) {
		this.houseOwner = houseOwner;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	@Override
	public void contact(String message, Person person) {
		if (person == houseOwner) {
			// 房东发的消息，通知租客
			tenant.getMessage(message);
		} else if (person == tenant) {
			// 租客发的消息，通知房东
			houseOwner.getMessage(message);
		}
	}

	public static void main(String[] args) {
		// 中介
		Ziroom mediator = new Ziroom();
		// 房主
		HouseOwner houseOwner = new HouseOwner("包租婆", mediator);
		// 租客
		Tenant tenant = new Tenant("打工人", mediator);
		mediator.setHouseOwner(houseOwner);
		mediator.setTenant(tenant);

		// 租客发求租信息
		tenant.contact("求一套两室一厅，亦庄附近");
		// 房主发出租信息
		houseOwner.contact("南北通透两居室，在马驹桥，是否有意？");
	}
}
