package com.lwq.common;

import java.io.Serializable;

/**
 * @author lwq
 * @date 2020/7/8 0008
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 3801400321439786248L;

	private long orderId;
	private String orderNo;
	private String orderDesc;

	public long getOrderId() {
		return orderId;
	}

	public Order(long orderId, String orderNo, String orderDesc) {
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.orderDesc = orderDesc;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", orderNo='" + orderNo + '\'' +
				", orderDesc='" + orderDesc + '\'' +
				'}';
	}
}
