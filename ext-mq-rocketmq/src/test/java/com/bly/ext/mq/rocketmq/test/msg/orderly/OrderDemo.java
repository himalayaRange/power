package com.bly.ext.mq.rocketmq.test.msg.orderly;

import java.io.Serializable;

public class OrderDemo implements Serializable{
	private static final long serialVersionUID = 5356207840377081888L;

	private Long orderId;
	
	private String desc;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "OrderDemo [orderId=" + orderId + ", desc=" + desc + "]";
	}
	
	
}
