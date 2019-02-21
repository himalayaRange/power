/*
 * Copyright 2008-2018 blysy.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */
package com.bly.ext.mq.rocketmq.test.msg.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 
 * Created by wangyi.
 */
public class OneWayProducer {
	// 需要中等可靠性的情况，如日志手机
	public static void main(String[] args) throws Exception{
		DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
		producer.start();
		for(int i = 0 ; i < 100 ; i ++) {
			Message message = new Message("TopicTest", "TagA" , ("Hello OneWayProducer").getBytes(RemotingHelper.DEFAULT_CHARSET));
			producer.sendOneway(message);
		}
		
		producer.shutdown();
	}
}
