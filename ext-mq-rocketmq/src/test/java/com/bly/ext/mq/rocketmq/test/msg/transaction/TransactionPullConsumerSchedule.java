package com.bly.ext.mq.rocketmq.test.msg.transaction;

import java.util.Date;
import java.util.List;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumerScheduleService;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullTaskCallback;
import org.apache.rocketmq.client.consumer.PullTaskContext;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MQPullConsumerScheduleService自动实现Pull Consumer 负载均衡
 * 
 * Created by wangyi.
 */
public class TransactionPullConsumerSchedule {
	private static final Logger logger = LoggerFactory.getLogger(TransactionPullConsumerSchedule.class);

	private static final String GROUP_NAME_CONSUMER = "TransConsumerGroupName";

	private final static String MQ_NAME_SRV_ADDR = "192.168.0.65:9876";
	
	private final static int NEXT_DELAY_TIME = 10 * 1000 ;

	public static void main(String[] args) throws MQClientException {
		final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService(GROUP_NAME_CONSUMER);
		scheduleService.getDefaultMQPullConsumer().setNamesrvAddr(MQ_NAME_SRV_ADDR);
		scheduleService.setMessageModel(MessageModel.CLUSTERING);
		scheduleService.registerPullTaskCallback("TransTopic", new PullTaskCallback() {
			
			@Override
			public void doPullTask(MessageQueue mq, PullTaskContext context) {
				System.out.println(new Date() + "-----------拉取数据开始-----------");
				MQPullConsumer consumer = context.getPullConsumer();
				try {
					long offset = consumer.fetchConsumeOffset(mq, false);
					if (offset < 0){offset = 0;}
					PullResult pullResult = consumer.pull(mq, "*", offset, 32);
					System.out.printf("%s%n", offset + "\t" + mq + "\t" + pullResult);
					switch (pullResult.getPullStatus()) {
						case FOUND: // 业务开始消费数据
							List<MessageExt> list = pullResult.getMsgFoundList();
							if(list != null && list.size() > 0){
								for(MessageExt ext : list) {
									try {
										System.out.println("Start Consumer Msg : " + new String(ext.getBody()));
									} catch (Exception e) {
										e.printStackTrace();
										logger.error("Consumer Msg ERROR ， INFO ：" , e.getMessage());
									}
								}
							}
							break;
						case NO_MATCHED_MSG:
							break;
						case NO_NEW_MSG:
						case OFFSET_ILLEGAL:
							break;
						default:
							break;
					}
					consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset()); // 更新offset并设置下次拉取数据时间（应该设置大些，至少大于5S），默认5S同步一次
					context.setPullNextDelayTimeMillis(NEXT_DELAY_TIME);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("MQPullConsumerScheduleService ERROR ，INFO : " + e.getMessage());
				}
				System.out.println(new Date() + "--------数据拉取结束-------");
			}
		});
		scheduleService.start();
	}
}
