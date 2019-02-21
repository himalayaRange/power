package com.bly.ext.mq.rocketmq.test.msg.transaction.execoter;

import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
/**
 * 
 * Created by wangyi.
 */
public class TransactionExecuterImpl implements LocalTransactionExecuter{

	@Override
	public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
		
		// Execute Local Transaction
		return LocalTransactionState.COMMIT_MESSAGE;
	}

}
