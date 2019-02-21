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
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 
 * Created by wangyi.
 */
public class AsyncProducer {
	// 可靠的异步发送，用于对响应时间敏感的业务场景
	public static void main(String[] args) throws Exception{
		DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
		producer.start();
		producer.setRetryTimesWhenSendAsyncFailed(0);
		for(int i =0 ; i < 100 ; i++) {
			final int index = i;
			Message message = new Message("TopicTest", "TagA", "OrderID188", ("Hello World AsyncProducer").getBytes(RemotingHelper.DEFAULT_CHARSET));
			producer.send(message, new SendCallback() {
				
				@Override
				public void onSuccess(SendResult sendResult) {
					System.out.printf("%-10d OK %s %n" , index , sendResult.getSendStatus());
				}
				
				@Override
				public void onException(Throwable e) {
					System.out.printf("%-10d Exception %s %n" , index , e);
				}
			});
		}
		producer.shutdown();
	}
}
