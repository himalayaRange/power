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
 * ]
 * Copyright  2018 CoderDream's Studio
 * All   right   reserved
 * Created   on  2018年1月22日 下午3:55:02 
 * Created by Wang.Yi
 */
public class TopicSender {

	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTemplate ;
	
	/**
	 * 发送消息到指定的订阅者
	 * @param topicName
	 * @param message
	 */
	public void sender(String topicName, final Serializable message) {
		jmsTemplate.send(topicName, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		});
	}
	
}
