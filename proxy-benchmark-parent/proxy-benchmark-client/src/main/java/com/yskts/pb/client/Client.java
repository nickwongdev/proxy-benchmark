package com.yskts.pb.client;

import com.yskts.pb.common.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;

public class Client implements Runnable {

	private final Bootstrap bootstrap;
	private Channel clientChannel;

	public Client(final EventLoopGroup childGroup, final String host, final int port) {
		this.bootstrap = new Bootstrap()
				.group(childGroup)
				.remoteAddress(host, port)
				.channel(EpollSocketChannel.class)
				.handler(new ClientHandler());
	}

	@Override
	public void run() {
		try {
			this.clientChannel = bootstrap.connect().sync().channel();
			clientChannel.writeAndFlush(Constants.UNRELEASABLE_REQUEST_BUFFER);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void stop() {
		System.out.println("Stopping Client...");
		if (clientChannel != null) {
			clientChannel.close();
		}
	}
}
