package com.bly.ext.mq.rocketmq.test.msg.simple;

import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {
	private static final String MQ_NAME_SRV_ADDR = "192.168.0.65:9876";
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	public static void main(String[] args) throws MQClientException, InterruptedException {
		// GroupName需要由应用保证为性
		DefaultMQProducer producer = new DefaultMQProducer("simpleMQGroupName");
		producer.setNamesrvAddr(MQ_NAME_SRV_ADDR);
		producer.setInstanceName("Producer");
		
		// Start是初始化的方法，切不可每次发送消息都调用start方法
		producer.start();
		
		/**
         * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
         * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
         * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
         * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
         */
		for(int i = 0 ; i < 10 ; i++){
			try {
					{
						Message msg = new Message("TopicTest1", // topic
								    "TagA" ,      // tag
								    "OrderID001",   // key
								    "Hello RocketMQ 001".getBytes(RemotingHelper.DEFAULT_CHARSET)); // body
						SendResult result = producer.send(msg);
						System.out.println(result);
					}
					{
						Message msg = new Message("TopicTest2", // topic
								    "TagB" ,      // tag
								    "OrderID002",   // key
								    "Hello RocketMQ 002".getBytes(RemotingHelper.DEFAULT_CHARSET)); // body
						SendResult result = producer.send(msg);
						System.out.println(result);
					}
			} catch (Exception e) {
				logger.error("Producer Message Faile,INFO:" , e.getMessage());
				e.printStackTrace();
			}
			TimeUnit.MILLISECONDS.sleep(1000);
		}
		producer.shutdown();
	}
}
