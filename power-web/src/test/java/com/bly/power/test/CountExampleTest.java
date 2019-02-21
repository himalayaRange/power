package com.bly.power.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class CountExampleTest {

	// 请求总数
	public static int clientTotal = 5000;

	// 同时并发数
	public static int threadTotal = 200;

	// 计数器
	public static AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		// 信号量，用于控制最大并发数
		final Semaphore semaphore = new Semaphore(threadTotal);
		// 闭锁， 可实现计数器的递减
		final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		long start = System.currentTimeMillis();
		for (int i = 0; i < clientTotal; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// 获取执行的许可，当总计未释放许可不超过200，允许执行，否则线程阻塞等待，直到获取许可
						semaphore.acquire();
						add();
						// 释放许可
						semaphore.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 闭锁减一
					countDownLatch.countDown();
				}
			});
		}
		// 闭锁阻塞等待，直到闭锁值等于0，阻塞才释放，继续往下执行
		countDownLatch.await();
		executorService.shutdown();
		long end = System.currentTimeMillis();
		System.out.println("count : " + count + "，耗时 ：" + (end - start) + "ms");
	}

	private static void add() {
		count.incrementAndGet();
	}
}
