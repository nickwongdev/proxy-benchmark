package com.yskts.pb.client;

import com.yskts.pb.common.Constants;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClientMain {

	private static final String DEFAULT_HOST = "localhost";
	private static final EventLoopGroup CHILD_GROUP = new EpollEventLoopGroup();
	private static final List<Client> CLIENT_LIST = new ArrayList<>();
	private static final String HOST = System.getProperty(Constants.PROXY_HOST_PROP_VAR, DEFAULT_HOST);
	private static final int PORT = Integer.getInteger(Constants.PROXY_PORT_PROP_VAR, 8080);
	private static final int INITIAL_CONNECTIONS = Integer.getInteger(Constants.INITIAL_CONNECTIONS_PROP_VAR, 1);
	private static final int MAX_CONNECTIONS = Integer.getInteger(Constants.MAX_CONNECTIONS_PROP_VAR, 1);
	private static final int INCREMENT_NUM = Integer.getInteger(Constants.INCREMENT_NUM_PROP_VAR, 1);
	private static final int INCREMENT_TIME = Integer.getInteger(Constants.INCREMENT_TIME_PROP_VAR, 1);
	private static final int TEST_DURATION = Integer.getInteger(Constants.TEST_DURATION_PROP_VAR, 1);

	public static void main(final String[] args) {
		for (int i = 0; i < INITIAL_CONNECTIONS; i++) {
			startClient();
		}
		ramp();

		CHILD_GROUP.schedule(ClientMain::shutdown, TEST_DURATION, TimeUnit.SECONDS);
		CHILD_GROUP.terminationFuture().awaitUninterruptibly();
		System.out.println("Terminated.");
	}

	private static void ramp() {
		if (CLIENT_LIST.size() < MAX_CONNECTIONS) {
			CHILD_GROUP.schedule(ClientMain::addClients, INCREMENT_TIME, TimeUnit.SECONDS);
		}
	}

	private static void addClients() {
		for (int i = 0; i < INCREMENT_NUM && CLIENT_LIST.size() <= MAX_CONNECTIONS; i++) {
			startClient();
		}
		ramp();
	}

	private static void startClient() {
		System.out.println("Adding Client...");
		final Client client = new Client(CHILD_GROUP, HOST, PORT);
		client.run();
		CLIENT_LIST.add(client);
	}

	private static void shutdown() {
		System.out.println("Shutting down...");
		CLIENT_LIST.forEach(Client::stop);
		CHILD_GROUP.shutdownGracefully();
	}
}
