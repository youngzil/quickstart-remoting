/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.quickstart.netty.v4x;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class NettyRemotingClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyRemotingClient.class);

    private static final long LOCK_TIMEOUT_MILLIS = 3000;

    private final int clientWorkerThreads = 10;
    private int connectTimeoutMillis = 3000;

    private int clientSocketSndBufSize = (1 << 16) - 1;
    private int clientSocketRcvBufSize = (1 << 16) - 1;

    private final Bootstrap bootstrap = new Bootstrap();
    private final EventLoopGroup eventLoopGroupWorker;
    private final Lock lockChannelTables = new ReentrantLock();

    private final Timer timer = new Timer("ClientHouseKeepingService", true);

    private final AtomicReference<List<String>> namesrvAddrList = new AtomicReference<List<String>>();
    private final AtomicReference<String> namesrvAddrChoosed = new AtomicReference<String>();
    private final AtomicInteger namesrvIndex = new AtomicInteger(initValueIndex());
    private final Lock lockNamesrvChannel = new ReentrantLock();

    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    public NettyRemotingClient() {

        int publicThreadNums = 4;

        this.eventLoopGroupWorker = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyClientSelector_%d", this.threadIndex.incrementAndGet()));
            }
        });
    }

    private static int initValueIndex() {
        Random r = new Random();

        return Math.abs(r.nextInt() % 999) % 999;
    }

    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(//
                clientWorkerThreads, //
                new ThreadFactory() {

                    private AtomicInteger threadIndex = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "NettyClientWorkerThread_" + this.threadIndex.incrementAndGet());
                    }
                });

        this.bootstrap.group(this.eventLoopGroupWorker).channel(NioSocketChannel.class)//
                .option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, false).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMillis)
                .option(ChannelOption.SO_SNDBUF, clientSocketSndBufSize).option(ChannelOption.SO_RCVBUF, clientSocketRcvBufSize).handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(defaultEventExecutorGroup,
                                // new NettyEncoder(),
                                // new NettyDecoder(),
                                new IdleStateHandler(0, 0, 120), // 120
                                new NettyConnectManageHandler(), new NettyClientHandler());
                    }
                });

        String addr = "127.0.0.1:9995";
        String[] s = addr.split(":");
        InetSocketAddress isa = new InetSocketAddress(s[0], Integer.parseInt(s[1]));

        ChannelFuture channelFuture = this.bootstrap.connect(isa);

    }

    public void shutdown() {
        try {

            this.eventLoopGroupWorker.shutdownGracefully();

            if (this.defaultEventExecutorGroup != null) {
                this.defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            logger.error("NettyRemotingClient shutdown exception, ", e);
        }

    }

    class NettyClientHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            // processMessageReceived(ctx, msg);
        }
    }

    class NettyConnectManageHandler extends ChannelDuplexHandler {
        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {

            // final String local = localAddress == null ? "UNKNOWN" : RemotingHelper.parseSocketAddressAddr(localAddress);
            // final String remote = remoteAddress == null ? "UNKNOWN" : RemotingHelper.parseSocketAddressAddr(remoteAddress);
            // log.info("NETTY CLIENT PIPELINE: CONNECT {} => {}", local, remote);
            //
            // super.connect(ctx, remoteAddress, localAddress, promise);
            //
            // if (NettyRemotingClient.this.channelEventListener != null) {
            // NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.CONNECT, remote, ctx.channel()));
            // }
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            // final String remoteAddress = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
            // log.info("NETTY CLIENT PIPELINE: DISCONNECT {}", remoteAddress);
            // closeChannel(ctx.channel());
            // super.disconnect(ctx, promise);
            //
            // if (NettyRemotingClient.this.channelEventListener != null) {
            // NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress, ctx.channel()));
            // }
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            // final String remoteAddress = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
            // log.info("NETTY CLIENT PIPELINE: CLOSE {}", remoteAddress);
            // closeChannel(ctx.channel());
            // super.close(ctx, promise);
            //
            // if (NettyRemotingClient.this.channelEventListener != null) {
            // NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.CLOSE, remoteAddress, ctx.channel()));
            // }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            // if (evt instanceof IdleStateEvent) {
            // IdleStateEvent event = (IdleStateEvent) evt;
            // if (event.state().equals(IdleState.ALL_IDLE)) {
            // final String remoteAddress = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
            // log.warn("NETTY CLIENT PIPELINE: IDLE exception [{}]", remoteAddress);
            // closeChannel(ctx.channel());
            // if (NettyRemotingClient.this.channelEventListener != null) {
            // NettyRemotingClient.this
            // .putNettyEvent(new NettyEvent(NettyEventType.IDLE, remoteAddress, ctx.channel()));
            // }
            // }
            // }

            ctx.fireUserEventTriggered(evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // final String remoteAddress = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
            // log.warn("NETTY CLIENT PIPELINE: exceptionCaught {}", remoteAddress);
            // log.warn("NETTY CLIENT PIPELINE: exceptionCaught exception.", cause);
            // closeChannel(ctx.channel());
            // if (NettyRemotingClient.this.channelEventListener != null) {
            // NettyRemotingClient.this.putNettyEvent(new NettyEvent(NettyEventType.EXCEPTION, remoteAddress, ctx.channel()));
            // }
        }
    }

}
