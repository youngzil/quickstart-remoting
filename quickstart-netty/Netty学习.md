http://netty.io/
https://github.com/netty/netty


文章
http://ifeve.com/netty1/


如果没有Netty？
远古：java.net + java.io
近代：java.nio
其他：Mina，Grizzly


https://www.cnblogs.com/claireyuancy/p/7343901.html
https://blog.csdn.net/gaowenhui2008/article/details/55044704


netty3.0：org.jboss.netty
netty4.0：io.netty



EventLoopGroup
EventLoop

NioServerSocketChannel
NioSocketChannel

ChannelInboundHandlerAdapter（Channel-Handler-Adapter）
SimpleChannelInboundHandler（Channel-Handler,in-handler,out-handler）

ChannelInitializer中的每个SocketChannel都有一个管道pipeline，保存我们之前注册的handler，组成一个链表，ChannelHandlerContext包装hander类
当有请求到来就执行链表的ChannelHandlerContext（内有我们定义的hander类）


接受连接----->创建一个新的NioSocketChannel到list----------->循环注册到一个 worker EventLoop 上-----清除list---> 注册selecot Read 事件。


Netty 的服务器端的 acceptor 阶段, 没有使用到多线程, 因此上面的 主从多线程模型 在 Netty 的服务器端是不存在的.

NioEventLoopGroup实际上就是个线程池
NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件
每一个NioEventLoop负责处理m个Channel
NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel

BossEventLoopGroup通常是一个单线程的EventLoop，EventLoop维护着一个注册了ServerSocketChannel的Selector实例，BossEventLoop不断轮询Selector将连接事件分离出来，通常是OP_ACCEPT事件，然后将accept得到的SocketChannel交给WorkEventLoopGroup，WorkerEventLoopGroup会由next选择其中一个EventLoopGroup来将这个SocketChannel注册到其维护的Selector并对其后续的IO事件进行持续的处理。在Reactor模式中BossEventLoopGroup主要是对多线程的扩展，而每个EventLoop的实现涵盖IO事件的分离，和分发(Dispatcher)。






