package com.yskts.pb.proxy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class ProxyInitializer extends ChannelInitializer<SocketChannel> {

	private final EventLoopGroup childGroup;

	public ProxyInitializer(final EventLoopGroup childGroup) {
		this.childGroup = childGroup;
	}

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		socketChannel.pipeline().addLast(new ProxyHandler(childGroup));
	}
}
