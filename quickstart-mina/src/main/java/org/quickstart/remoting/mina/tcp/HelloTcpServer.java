package org.quickstart.remoting.mina.tcp;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.quickstart.remoting.mina.handler.HelloServerHandler;

/**
 * 基于Tcp的服务端
 * 
 * @author welcome
 *
 */
public class HelloTcpServer {

    private static final int PORT = 9898;

    public static void main(String[] args) throws Exception {
        IoAcceptor acceptor = new NioSocketAcceptor(); // TCP Acceptor
        acceptor.getFilterChain().addLast("logging", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        acceptor.getFilterChain().addLast("mdc", new MdcInjectionFilter());
        acceptor.setHandler(new HelloServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.bind(new InetSocketAddress(PORT));
    }

}
