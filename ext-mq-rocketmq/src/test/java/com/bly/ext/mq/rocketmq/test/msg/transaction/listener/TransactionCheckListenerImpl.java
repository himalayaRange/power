package com.bly.ext.mq.rocketmq.test.msg.transaction.listener;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;
// 未决事务，服务器回查客户端
public class TransactionCheckListenerImpl implements TransactionCheckListener{

	@Override
	public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
		// 由于迟迟没有收到消息的确认消息，因此主动询问这条prepare消息，是否正常？
		
		// 可以通过查询数据库，看这条消息是否已经处理
		System.out.println("localTransactionState msg :" + msg.getBody());
		return LocalTransactionState.COMMIT_MESSAGE;
	}

}
