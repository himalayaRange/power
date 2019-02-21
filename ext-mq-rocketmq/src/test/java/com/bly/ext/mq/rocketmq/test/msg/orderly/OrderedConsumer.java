package com.bly.ext.mq.rocketmq.test.msg.orderly;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
/**
 * 顺序消费，带事务方式（应用可控制offset什么时候提交）
 * 
 * Created by wangyi.
 */
public class OrderedConsumer {
	private static final String GROUP_NAME_CONSUMER_ORDERLY = "GROUP_NAME_CONSUMER_ORDERLY";
	
	private static final String MQ_NAME_SRV_ADDR = "192.168.0.65:9876";
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP_NAME_CONSUMER_ORDERLY);
		consumer.setNamesrvAddr(MQ_NAME_SRV_ADDR);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		// TagA 、 TagC 、 TagD代表主题下的不同队列，不同的队列代表不同的
		consumer.subscribe("TopicTestOrderly", "TagA || TagC || TagD");
		consumer.registerMessageListener(new MessageListenerOrderly() {
			Random random = new Random();
			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				context.setAutoCommit(true);
				System.out.println(Thread.currentThread().getName() + "Received Message : ");
				for(MessageExt ext : msgs){
					System.out.println(ext + ",content:" + new String(ext.getBody()));
				}
				try {
					// 模拟业务逻辑处理中...  
					TimeUnit.SECONDS.sleep(random.nextInt(10));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});
		
		consumer.start();
		System.out.println("Consumer Started ...");
	}
}
