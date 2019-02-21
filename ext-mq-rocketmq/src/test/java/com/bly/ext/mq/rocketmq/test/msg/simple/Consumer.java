package com.bly.ext.mq.rocketmq.test.msg.simple;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
/**
 * 
 * Created by wangyi.
 */
public class Consumer {
	private static final String MQ_NAME_SRV_ADDR = "192.168.0.65:9876";
	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
		consumer.setNamesrvAddr(MQ_NAME_SRV_ADDR);
		consumer.setInstanceName("Consumber");
		/**
		 *  consumeMessageBatchMaxSize：参数批量接收消息数量
		 *  ConsumeFromWhere：设置Consumer第一次启动是从队列头部开始消费还是尾部消费，如果非第一次启动，那么按照上次消费的位置继续消费
		 */
		consumer.setConsumeMessageBatchMaxSize(50);
		consumer.setConsumeThreadMax(25);
		consumer.setConsumeThreadMin(15);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		// 订阅指定的topic下tags分别等于TagA或TagB,若指定所有消息，则使用*
		consumer.subscribe("TopicTest1", "TagA");
		consumer.subscribe("TopicTest2", "TagB");
		
		// 如果设置成广播模式，那么所有的消费者都将受到所有的消息，否则消费者将进行负载均衡
		 consumer.setMessageModel(MessageModel.CLUSTERING); // 集群，默认
		// consumer.setMessageModel(MessageModel.BROADCASTING); // 广播模式
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.println(Thread.currentThread().getName() + " Receive New Messages :" +msgs.size());
				try {
					for(MessageExt msg : msgs){
						if(msg.getTopic().equals("TopicTest1")){
							// 执行对应的Tag逻辑
							if(msg.getTags() != null && msg.getTags().equals("TagA")){
								// 执行TagA逻辑
								System.out.println("TopicTest1----->TagA----->" + new String(msg.getBody()));
							}else if(msg.getTags() != null && msg.getTags().equals("TagB")){
								// 执行TagB逻辑
								System.out.println("TopicTest1----->TagB----->" + new String(msg.getBody()));
							}else{
								System.err.println("----------TOPIC1 ERROR DATA--------------");
							}
						}else if(msg.getTopic().equals("TopicTest2")){
							if(msg.getTags() != null && msg.getTags().equals("TagA")){
								System.out.println("TopicTest2----->TagA----->" + new String(msg.getBody()));
							}else if(msg.getTags() != null && msg.getTags().equals("TagB")){
								System.out.println("TopicTest2----->TagB----->" + new String(msg.getBody()));
							}else{
								System.err.println("----------TOPIC2 ERROR DATA--------------");
							}
						}else{
							System.err.println("----------No Math Toic--------------");
						}
					}
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;// 重试
				}
			}
		});
		
		consumer.start();
		System.out.println("--------Consumer Started ....--------");
	}
}
