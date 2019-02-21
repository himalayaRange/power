package com.bly.ext.mq.rocketmq.test.msg.orderly;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 顺序消息生产者
 * 
 * Created by wangyi.
 */
public class OrderedProducer {
	private static final String GROUP_NAME_PRODUCER_ORDERLY = "GROUP_NAME_PRODUCER_ORDERLY";
	
	private static final String MQ_NAME_SRV_ADDR = "192.168.0.65:9876";
	
	public static void main(String[] args) {
		try {
			DefaultMQProducer producer = new DefaultMQProducer(GROUP_NAME_PRODUCER_ORDERLY);
			producer.setNamesrvAddr(MQ_NAME_SRV_ADDR);
			producer.start();
			String[] tags = new String[]{"TagA" , "TagC" , "TagD"};
			List<OrderDemo> orderList =  buildOrders();  
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateStr = sdf.format(date);  
	        for(int i = 0 ; i < 5 ; i ++) {
	        	String body = dateStr + " Hello RocketMQ" + orderList.get(i).toString();
	        	Message message = new Message("TopicTestOrderly", tags[i % tags.length], "KEY" + i, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
	        	// RocketMQ通过MessageQueue中的算法来确定消息发送到哪一个队列上
	        	// RocketMQ默认提供默认的MessageQueueSelector实现： 随机/Hash
	        	// 可以根据具体的业务实现自己的MessageQueueSelector来决定消息按某种策略发送到消息队列中
	        	SendResult sendResult  = producer.send(message, new MessageQueueSelector() {
					
					@Override
					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
						System.out.println("arg:" + arg);
						Long id = (Long)arg;
						long index = id % mqs.size();
						return mqs.get((int)index);
					}
				}, orderList.get(i).getOrderId());
	        	 System.out.println(sendResult + ", body:" + body); 
	        }
	        
	        producer.shutdown();
		} catch (MQClientException e) {  
            e.printStackTrace();  
        } catch (RemotingException e) {  
            e.printStackTrace();  
        } catch (MQBrokerException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
	}
	
	private static List<OrderDemo> buildOrders(){
		List<OrderDemo> orderList = new ArrayList<OrderDemo>();  
		OrderDemo orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("创建");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111065L);
        orderDemo.setDesc("创建");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("付款");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103117235L);  
        orderDemo.setDesc("创建");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111065L);  
        orderDemo.setDesc("付款");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103117235L);  
        orderDemo.setDesc("付款");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111065L);  
        orderDemo.setDesc("完成");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("推送");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103117235L);  
        orderDemo.setDesc("完成");  
        orderList.add(orderDemo);  
          
        orderDemo = new OrderDemo();  
        orderDemo.setOrderId(15103111039L);  
        orderDemo.setDesc("完成");  
        orderList.add(orderDemo);  
          
        return orderList;  
	}
}
