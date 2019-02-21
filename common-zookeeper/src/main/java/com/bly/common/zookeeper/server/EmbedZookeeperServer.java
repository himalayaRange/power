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
package com.bly.common.zookeeper.server;

import java.io.File;
import java.io.IOException;
import org.apache.curator.test.TestingServer;

/**
 * 内存版的内嵌Zookeeper
 *
 * @author wangyi
 */
public class EmbedZookeeperServer {

	private static TestingServer testingServer;

	public void start(final int port) {
		try {
			testingServer = new TestingServer(port, new File(String.format("target/test_zk_data/%s/", System.nanoTime())));
		} catch (final Exception ex) {
			ex.printStackTrace();
		} finally {
			Runtime.getRuntime().addShutdownHook(new Thread() {

				@Override
				public void run() {
					try {
						Thread.sleep(1000L);
						testingServer.close();
					} catch (final InterruptedException   | IOException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
	}
}
