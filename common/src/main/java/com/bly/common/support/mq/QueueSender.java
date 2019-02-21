package com.bly.common.support.mq;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 队列消息发送
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月22日 下午3:03:34 
 * Created by Wang.Yi
 */
public class QueueSender {

	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送一条消息到指定队列
	 * @param queueName
	 * @param message
	 */
	public  void send(String queueName , final Serializable message) {
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});
	}
}
