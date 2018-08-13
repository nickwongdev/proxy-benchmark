package com.yskts.pb.proxy;

import com.yskts.pb.common.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

public class ProxyMain {

	public static void main(final String[] args) {

		try {

			final EventLoopGroup bossGroup = new EpollEventLoopGroup(1);
			final EventLoopGroup childGroup = new EpollEventLoopGroup();

			ServerBootstrap b = new ServerBootstrap()
					.channel(EpollServerSocketChannel.class)
					.group(bossGroup, childGroup)
					.childHandler(new ProxyInitializer(childGroup));
			Channel c = b.bind(Constants.PROXY_PORT).sync().channel();
			c.closeFuture().sync();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
