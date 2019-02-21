package com.bly.common.aspect;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;
import com.bly.common.exception.DataSourceAspectException;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	
	private DataSource master; // 主库，只允许有一个
	
	private Map<String, DataSource> slaves; // 从库，允许有多个
	
	private AtomicLong slaveCount = new AtomicLong();
	
	private int slaveSize = 0;
	
	private Map<Object, Object> dataSources = new HashMap<Object, Object>();
	
	private static final String DEFAULT = "master";
	
	private static final String SLAVE = "slave";
	
	private static final ThreadLocal<LinkedList<String>> datasourceHolder = new ThreadLocal<LinkedList<String>>() {
		@Override
		protected LinkedList<String> initialValue() {
			return new LinkedList<String>();
		}
	};
	
	private static final ThreadLocal<String> currDataSourceHolder = new ThreadLocal<String>();

	/**
	 *  初始化
	 */
	@Override
	public void afterPropertiesSet() {
		if (null == master) {
			throw new IllegalArgumentException("Property 'master' is required");
		}
		dataSources.put(DEFAULT, master);
		if (null != slaves && slaves.size() > 0) {
			 // for (int i = 0; i < slaves.size(); i++) { //负载均衡使用
			 // dataSources.put(SLAVE + (i + 1), slaves.get(i)); }
			for (Map.Entry<String, DataSource> entry : slaves.entrySet()) {
				String key = entry.getKey();
				DataSource value = entry.getValue();
				dataSources.put(new StringBuffer().append(SLAVE).append(key).toString(), value);
			}
			slaveSize = slaves.size();
		}
		this.setDefaultTargetDataSource(master);
		this.setTargetDataSources(dataSources);
		super.afterPropertiesSet();
	}

	/**
	 * 选择使用主库，并把选择放到当前ThreadLocal的栈顶
	 */
	public static void useMaster() {
		if (logger.isDebugEnabled()) {
			logger.debug("use datasource :" + datasourceHolder.get());
		}
		LinkedList<String> m = datasourceHolder.get();
		m.offerFirst(DEFAULT);
	}

	/**
	 * 选择使用从库，并把选择放到当前ThreadLocal的栈顶
	 */
	public static void useSlave(String dbName) {
		if (logger.isDebugEnabled()) {
			logger.debug("use datasource :" + datasourceHolder.get());
		}
		if (StringUtils.isEmpty(dbName)) {
			throw new DataSourceAspectException("未选择从库数据库，请选择从库再试！");
		}
		currDataSourceHolder.set(dbName);
		LinkedList<String> m = datasourceHolder.get();
		m.offerFirst(SLAVE);
	}

	/**
	 * 重置当前栈
	 */
	public static void reset() {
		LinkedList<String> m = datasourceHolder.get();
		if (logger.isDebugEnabled()) {
			logger.debug("reset datasource {}", m);
		}
		if (m.size() > 0) {
			m.poll();
		}
	}

	/**
	 * 如果是选择使用从库，且从库的数量大于1，则通过取模来控制从库的负载, 计算结果返回AbstractRoutingDataSource
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		LinkedList<String> m = datasourceHolder.get();
		String DB = currDataSourceHolder.get();
		String key = m.peekFirst() == null ? DEFAULT : m.peekFirst();
		if (logger.isDebugEnabled()) {
			logger.debug("currenty datasource :" + key);
		}
		if (null != key) {
			if (DEFAULT.equals(key)) {
				return key; // master
			} else if (SLAVE.equals(key)) {
				return new StringBuffer().append(SLAVE).append(DB).toString();
				// Slave loadBalance
				// if (slaveSize > 1) {
				// long c = slaveCount.incrementAndGet();
				// c = c % slaveSize;
				// return SLAVE + (c + 1);
				// } else {
				// return SLAVE + "1"
				// }
			}
			return null;
		} else {
			return null;
		}
	}

	public DataSource getMaster() {
		return master;
	}

	public Map<String, DataSource> getSlaves() {
		return slaves;
	}

	public void setMaster(DataSource master) {
		this.master = master;
	}

	public void setSlaves(Map<String, DataSource> slaves) {
		this.slaves = slaves;
	}

}
