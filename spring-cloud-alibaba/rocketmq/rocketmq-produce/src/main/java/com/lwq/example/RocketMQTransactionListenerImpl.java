package com.lwq.example;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

/**
 * @author lwq
 * @date 2021/6/11 0011
 */
@RocketMQTransactionListener(txProducerGroup = "myTxProducerGroup", corePoolSize = 5, maximumPoolSize = 10)
public class RocketMQTransactionListenerImpl implements RocketMQLocalTransactionListener {

	/**
	 * 执行本地事务，根据结果发送给broker相应事务确认消息
	 */
	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object o) {
		Object num = msg.getHeaders().get("test");
		if ("1".equals(num)) {
			System.out.println(
					"executer: " + new String((byte[]) msg.getPayload()) + " unknown");
			// 返回UNKNOWN，broker将回查事务状态（checkLocalTransaction）
			return RocketMQLocalTransactionState.UNKNOWN;
		}
		else if ("2".equals(num)) {
			System.out.println(
					"executer: " + new String((byte[]) msg.getPayload()) + " rollback");
			// 返回ROLLBACK，broker不会将事务给消费端
			return RocketMQLocalTransactionState.ROLLBACK;
		}

		System.out.println(
				"executer: " + new String((byte[]) msg.getPayload()) + " commit");
		return RocketMQLocalTransactionState.COMMIT;

	}

	/**
	 * broker获取不到事务确认消息，回查事务状态
	 */
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
		System.out.println("check: " + new String((byte[]) msg.getPayload()));
		return RocketMQLocalTransactionState.COMMIT;
	}
}
