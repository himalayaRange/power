package com.bly.ext.rdb.sharding.jdbc.namespace.service;

import org.springframework.stereotype.Service;
import com.bly.ext.rdb.sharding.jdbc.namespace.entity.Order;
import com.bly.ext.rdb.sharding.jdbc.namespace.entity.OrderItem;
import com.bly.ext.rdb.sharding.jdbc.namespace.repository.OrderItemRepository;
import com.bly.ext.rdb.sharding.jdbc.namespace.repository.OrderRepository;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private OrderItemRepository orderItemRepository;

	public void init() {
		orderRepository.createIfNotExistsTable();
		orderItemRepository.createIfNotExistsTable();
		orderRepository.truncateTable();
		orderItemRepository.truncateTable();
		List<Long> orderIds = new ArrayList<Long>(10);
		System.out.println("1.Insert--------------");
		for (int i = 0; i < 10; i++) {
			Order order = new Order();
			order.setUserId(i + 1);
			order.setStatus("INSERT_TEST");
			orderRepository.insert(order);
			long orderId = order.getOrderId();
			orderIds.add(orderId);

			OrderItem item = new OrderItem();
			item.setOrderId(orderId);
			item.setUserId(i + 1);
			item.setStatus("INSERT_TEST");
			orderItemRepository.insert(item);
		}
		System.out.println(orderItemRepository.selectAll());
	}

	public void destoryTable() {
		System.out.println("2.DropTable--------------");
		orderItemRepository.dropTable();
		orderRepository.dropTable();
	}
}
