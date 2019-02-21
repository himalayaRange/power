package com.bly.ext.mq.rocketmq.test.msg.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
/**
 *  Pull是客户端主动去拉消息，未实现负载均衡，需要自己实现负载均衡 MessageQueueListener.
 *  Push是注册了一个回调，当有新消息，该回调被调用，已经实现了负载均衡.
 *  
 * Created by wangyi.
 */
public class TransactionPullConsumer {
	private static final Map<MessageQueue,Long> offseTable = new HashMap<MessageQueue,Long>();
	
	private static final String GROUP_NAME_CONSUMER = "TransConsumerGroupName";
	
	private final static String MQ_NAME_SRV_ADDR = "192.168.0.65:9876";
	
	public static void main(String[] args) throws MQClientException {
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(GROUP_NAME_CONSUMER);
		consumer.setNamesrvAddr(MQ_NAME_SRV_ADDR);
		consumer.setInstanceName("transactionConsumer");
		consumer.start();
		Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TransTopic"); // 拉取定义主题的队列，默认队列大小是4
		for(MessageQueue mq : mqs){
			System.out.println("Consume from the queue : " + mq);
			SINGLE_MQ : while(true){
				try {
					PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32); // 遍历所有queue，挨个调用pull
					List<MessageExt> list = pullResult.getMsgFoundList();
					if(list != null && list.size() < 100) {
						for(MessageExt ext : list) {
							System.out.println("Received msg :" +new String(ext.getBody()));
						}
					}
					putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
					switch(pullResult.getPullStatus()) {
						case FOUND:
							break;
						case NO_MATCHED_MSG:
							break;
						case NO_NEW_MSG:
							break SINGLE_MQ;
						case OFFSET_ILLEGAL:
							break;
						default:
							break;
 					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		consumer.shutdown();
	}
	
	private static void putMessageQueueOffset(MessageQueue mq , Long offset){
		offseTable.put(mq, offset);
	}
	
	private static Long getMessageQueueOffset(MessageQueue mq){
		Long offset = offseTable.get(mq);
		if(offset != null){
			System.out.println("offset " + offset);
			return offset;
		}
		return 0L;
	}
}
