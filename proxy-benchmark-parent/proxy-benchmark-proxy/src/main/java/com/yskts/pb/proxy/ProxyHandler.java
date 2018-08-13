package com.yskts.pb.proxy;

import com.yskts.pb.common.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;

/**
 * Connects to the Server and forwards all packets to it
 */
public class ProxyHandler extends ChannelInboundHandlerAdapter {

	public static final String DEFAULT_HOST = "localhost";
	private final EventLoopGroup childGroup;

	private Channel clientChannel;

	public ProxyHandler(final EventLoopGroup childGroup) {
		this.childGroup = childGroup;
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		final String serverHostname = System.getProperty(Constants.SERVER_HOST_PROP_VAR, DEFAULT_HOST);
		final Bootstrap bootstrap = new Bootstrap()
				.group(childGroup)
				.channel(EpollSocketChannel.class)
				.handler(new ProxyClientHandler(ctx.channel()));
		this.clientChannel = bootstrap.connect(serverHostname, Constants.SERVER_PORT).sync().channel();

		// Close the other if one goes away
		ctx.channel().closeFuture().addListener(future -> clientChannel.close());
		clientChannel.closeFuture().addListener(future -> ctx.channel().close());

		ctx.fireChannelRegistered();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		clientChannel.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		clientChannel.flush();
	}

	private static class ProxyClientHandler extends ChannelInboundHandlerAdapter {

		private final Channel serverChannel;

		ProxyClientHandler(Channel serverChannel) {
			this.serverChannel = serverChannel;
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			serverChannel.write(msg);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			serverChannel.flush();
		}
	}
}
