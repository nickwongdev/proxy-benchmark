package com.yskts.pb.server;

import com.yskts.pb.common.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

/**
 * Serves a static amount of json content (1kb)
 */
public class ServerMain {

	public static void main(final String[] args) {

		try {
			ServerBootstrap b = new ServerBootstrap()
					.channel(EpollServerSocketChannel.class)
					.group(new EpollEventLoopGroup(1), new EpollEventLoopGroup())
					.childHandler(new ServerHandler());
			Channel c = b.bind(Constants.SERVER_PORT).sync().channel();
			c.closeFuture().sync();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
