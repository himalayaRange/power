package com.bly.ext.mq.rocketmq.test.msg.transaction;

import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import com.bly.ext.mq.rocketmq.test.msg.transaction.execoter.TransactionExecuterImpl;
import com.bly.ext.mq.rocketmq.test.msg.transaction.listener.TransactionCheckListenerImpl;

public class TransactionProducer {
	final static String GROUP_NAME_PRODUCER = "TransProducerGroupName"; // 1.消息组要由应用保证唯一性
	
	final static String MQ_NAME_SRV_ADDR = "192.168.0.65:9876";

	public static void main(String[] args) throws MQClientException, InterruptedException {
		final TransactionMQProducer producer = new TransactionMQProducer(GROUP_NAME_PRODUCER);
		producer.setNamesrvAddr(MQ_NAME_SRV_ADDR);
		producer.setInstanceName("transactionProducer");

		producer.start();

		// 服务器回调producer，
		producer.setTransactionCheckListener(new TransactionCheckListener() {

			@Override
			public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
				System.out.println("checkLocalTransactionStatr : " + new String(msg.getBody()));
				// 更具消息来设置消息的状态
				return LocalTransactionState.COMMIT_MESSAGE;
			}
		});
		// 设置事务判决处理类
		// producer.setTransactionCheckListener(new TransactionCheckListenerImpl());

		for (int i = 0; i < 5; i++) {
			try {
				{
					Message msg = new Message("TransTopic", "TagA", "Trans001",	("RocketMQ 事务消息").getBytes(RemotingHelper.DEFAULT_CHARSET));
					producer.sendMessageInTransaction(msg, new LocalTransactionExecuter() {

						@Override
						public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
							System.out.println("executeLocalTransactionBranch msg :" + new String(msg.getBody()));
							System.out.println("executeLocalTransactionBranch arg :" + arg);
							return LocalTransactionState.COMMIT_MESSAGE;
						}
					}, "$$$");
					
					// producer.sendMessageInTransaction(msg, new TransactionExecuterImpl(), "$$$");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			TimeUnit.MILLISECONDS.sleep(1000);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				producer.shutdown();
			}
		}));
		System.exit(0);
	}
}
