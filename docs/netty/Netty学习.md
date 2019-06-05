netty概念和常用类：group、channel、handler、option
netty执行流程
Server端Boss线程和worker线程比较：boss设置为1，我猜测是为了减少线程切换
netty两种线程模型和JDK线程池模型比较
Netty的高性能及NIO的epoll空轮询bug：判定和解决：重建selector
TCP粘包/拆包与Netty解决方案：
netty的网络创建在哪里
java nio的selector  和linux的epoll select
Java异步NIO框架Netty实现高性能高并发：netty高性能
                         传输：异步非阻塞通信、零拷贝（直接内存和transferTo）、灵活的TCP参数配置能力（option参数的设置）
                         协议：Protobuf的支持、Thrift的
                         线程：高效的Reactor线程模型、线程池、无锁化的串行设计理念：Netty的NioEventLoop是单线程的


Netty实现原理
netty的相关所有，使用的协议
hession用来干嘛的：Hessian本身即是基于Http的RPC实现
Java序列化和Hessian序列化的区别
netty的相关，管道是什么设计模式：责任链模式 或者 管道设计模式


Netty零拷贝
Netty内存管理：堆外内存池

---------------------------------------------------------------------------------------------------------------------



http://netty.io/
https://github.com/netty/netty


文章
http://ifeve.com/netty1/


如果没有Netty？
远古：java.net + java.io
近代：java.nio
其他：Mina，Grizzly


https://blog.csdn.net/xiaolang85/article/details/37873059
https://blog.csdn.net/hbtj_1216/article/details/75331995
https://blog.csdn.net/gaowenhui2008/article/details/55044704


netty3.0：org.jboss.netty
netty4.0：io.netty


示例查看
org.quickstart.remoting.netty.v4x.helloworld
Rocketmq中的连接:NettyRemotingClient、NettyRemotingServer



group、channel、handler、option

客户端：
Bootstrap
NioEventLoopGroup
NioSocketChannel
Bootstrap.connect(host, port).sync()

服务端：
ServerBootstrap
Boss和worker：EpollEventLoopGroup 或者 NioEventLoopGroup
EpollServerSocketChannel 或者 NioServerSocketChannel 
ServerBootstrap.bind(portNumber).sync()


netty定义了两种类型的线程
I/O线程: EventLoop, EventLoopGroup。
业务线程: EventExecutor, EventExecutorGroup。

每个EventLoop和EventExecutor是一个单独的线程，可以执行Runnable任务。
是SingleThreadEventLoop和SingleThreadEventExecutor


ChannelInboundHandlerAdapter（Channel-Handler-Adapter）
SimpleChannelInboundHandler（Channel-Handler,in-handler,out-handler）

ChannelOutboundHandlerAdapter


ChannelOption选项参考
https://www.jianshu.com/p/975b30171352

ChannelOption和套接字选项中对应

ChannelOption.SO_KEEPALIVE
ChannelOption.TCP_NODELAY
ChannelOption.CONNECT_TIMEOUT_MILLIS
ChannelOption.SO_SNDBUF和ChannelOption.SO_RCVBUF

.option(ChannelOption.SO_BACKLOG, 1024)   backlog参数指定了队列的大小
.option(ChannelOption.SO_REUSEADDR, true)  这个参数表示允许重复使用本地地址和端口，
.option(ChannelOption.SO_KEEPALIVE, false)  当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
.childOption(ChannelOption.TCP_NODELAY, true)     该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
.childOption(ChannelOption.SO_SNDBUF, nettyServerConfig.getServerSocketSndBufSize())  这两个参数用于操作接收缓冲区和发送缓冲区的大小，接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。
.childOption(ChannelOption.SO_RCVBUF, nettyServerConfig.getServerSocketRcvBufSize())  这两个参数用于操作接收缓冲区和发送缓冲区的大小，接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。




netty执行流程：

ChannelInitializer中的每个SocketChannel都有一个管道pipeline，保存我们之前注册的handler，组成一个链表，ChannelHandlerContext包装hander类
当有请求到来就执行链表的ChannelHandlerContext（内有我们定义的hander类）


接受连接----->创建一个新的NioSocketChannel到list----------->循环注册到一个 worker EventLoop 上-----清除list---> 注册selecot Read 事件。


Netty 的服务器端的 acceptor 阶段, 没有使用到多线程, 因此上面的 主从多线程模型 在 Netty 的服务器端是不存在的.

NioEventLoopGroup实际上就是个线程池
NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件
每一个NioEventLoop负责处理m个Channel
NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel

BossEventLoopGroup通常是一个单线程的EventLoop，EventLoop维护着一个注册了ServerSocketChannel的Selector实例，BossEventLoop不断轮询Selector将连接事件分离出来，通常是OP_ACCEPT事件，然后将accept得到的SocketChannel交给WorkEventLoopGroup，WorkerEventLoopGroup会由next选择其中一个EventLoopGroup来将这个SocketChannel注册到其维护的Selector并对其后续的IO事件进行持续的处理。在Reactor模式中BossEventLoopGroup主要是对多线程的扩展，而每个EventLoop的实现涵盖IO事件的分离，和分发(Dispatcher)。




Server端Boss线程和worker线程比较：

bossGroup线程组实际就是Acceptor线程池，负责处理客户端的TCP连接请求，如果系统只有一个服务端端口需要监听，则建议bossGroup线程组线程数设置为1。
workerGroup是真正负责I/O读写操作的线程组，通过ServerBootstrap的group方法进行设置，用于后续的Channel绑定。默认是可用的CPU内核数 × 2。



BossGroup和WorkerGroup都是NioEventLoopGroup，BossGroup用来处理nio的Accept，Worker处理nio的Read和Write事件

在Netty的里面有一个Boss，他开了一家公司（开启一个服务端口）对外提供业务服务，它手下有一群做事情的workers。Boss一直对外宣传自己公司提供的业务，并且接受(accept)有需要的客户(client)，当一位客户找到Boss说需要他公司提供的业务，Boss便会为这位客户安排一个worker，这个worker全程为这位客户服务（read/write）。如果公司业务繁忙，一个worker可能会为多个客户进行服务。这就是Netty里面Boss和worker之间的关系。，





netty线程模型和JDK线程池模型比较：
1、netty线程切换几率小，更高效：TPE使用共用的队列排队，在高并发环境下会导致BlockingQueue频繁的锁碰撞，进而导致大量线程切换开销，MEG中由于队列是只有一个线程消费，BlockingQueue锁碰撞机会比TPE小很多，线程切换开销也比TPE小很多
2、netty保证执行顺序：TPE任务执行没有顺序，取出任务交给线程执行，MEG中由于队列是只有一个线程消费，可以保证执行顺序和入队顺序一致，
  如: 在Channel上先后触发了connect, read, close事件，如果业务上要求收到close事件后不再处理read事件, 如果执行先后顺序不能保证，很有可能执行不到read的业务。这种类似业务场景在基于TCP协议的服务器中很常见，这一点TPE不能支持，而MEG能够很好地支持这些对任务执行顺序有要求的场景。

---------------------------------------------------------------------------------------------------------------------
参考
https://blog.csdn.net/weixin_39818173/article/category/8521660


netty是一个基于事件驱动的框架，它把事件分成两种类型：输入事件(inbound)和输出事件(outbound)， 整个框架都是围绕事件处理进行设计的，以下是netty的核心架构：

EventLoopGroup: 在Channel上执行I/O的线程组，netty把这个线程组中的线程定义为I/O线程，后面会讲到，有些特定的事件必须在I/O线程中处理。
EventExecutorGroup: 用来执行ChannelHandlerContext和ChannelHandler中回调方法的线程。在用户向ChannelPipeline中注册一个ChannelHanlder时，如果指定了一个EventExecutorGroup，那么它和它对应的ChannelHandlerContext都会在指定的EventExecutorGroup执行，否则，在Channel的EventLoopGroup中执行。



netty的高并发能力很大程度上由它的线程模型决定的，netty定义了两种类型的线程：

I/O线程: EventLoop, EventLoopGroup。一个EventLoopGroup包含多个EventLoop, 每个Channel会被注册到一个，一个EventLoop中, 一个EventLoop可以包含多个Channel。Channel的Unsafe实例的方法必须要在EventLoop中执行(netty中明确指明的不需要在I/O线程中执行的几个方法除外，前面的章节中有详细的讲解)。

业务线程: EventExecutor, EventExecutorGroup。一个EventExecutorGroup包含多个EventExecutor。当用户向Channel的pipeline注册一个ChannelHandler时，可以指定一个EventExecutorGroup，这个ChannelHanndler的所有方法都会被放到EventExecutorGroup的中的一个EventExecutor中执行。 当用户没有为这个ChannelHandler明确指定EventExecutorGroup时，这个ChannelHandler会被放到Channel所属的EventLoop中执行。



netty线程模型和JDK线程池模型比较：

为了方便描述，先定义几个简称
ThreadPoolExecuto: TPE
MultithreadEventExecutorGroup: MEG
SingleThreadEventExecutor: STE

接下来，对比一下TPE和MEG

线程管理: TPE负责管理线程，根据传入的参数，运行过程中动态调节线程数，它也可以让线程一直保持在一个稳定的数量。MEG不负责管理线程，它只负责创建指定数量的STE, 每个STE只维护一个线程，保证有且只有一个线程。

任务排队: TPE维护一个所有线程共用的任务队列，所有线程都从同一队列中取任务。MEG没任务队列，它只负责把任务派发到一个STE, 默认的派发策略是轮询。每个STE维护一个私有的任务队列，STE会把任务放入私有的队列中排队，这队列只有STE维护的线程才能消费。

任务提交和执行: TPE把任务当成无关联的独立任务执行，不保证任务的执行顺序和execute的调用顺序一致, TPE认为任务的顺序不重要。MEG提交任务的方式有两种, （1）直接调用MEG的execute方法提交任务，这个方式，和TPE一样，不关心任务的执行顺序；（2）先从MEG中取出一个STE，然后调用STE的excute，这种方式任务的执行顺序和execute调用顺序一致。

性能: TPE使用共用的队列排队，在高并发环境下会导致BlockingQueue频繁的锁碰撞，进而导致大量线程切换开销，MEG中由于队列是只有一个线程消费，BlockingQueue锁碰撞机会比TPE小很多，线程切换开销也比TPE小很多，因此，可以得出结论，如果任务本身不会导致线程阻塞，MEG性能比TPE高, 否则MEG没有优势。
 

到这里已经可以回答前面提出的问题了: MEG把任务当成事件来看待，每个事件和特定的Channel关联(这一点由EventLoopGroup接口体现, 它定义了一个register(Channel channel)方法), 而一个特定Channel上触发的一系列事件，处理顺序和触发顺序必须要一致，如: 在Channel上先后触发了connect, read, close事件，如果业务上要求收到close事件后不再处理read事件, 如果执行先后顺序不能保证，很有可能执行不到read的业务。这种类似业务场景在基于TCP协议的服务器中很常见，这一点TPE不能支持，而MEG能够很好地支持这些对任务执行顺序有要求的场景。这就是netty要另外设计自己的线程模型的主要原因。



ChannelPipeline的设计

链表管理的方法
添加：addFirst, addLast, addAfter, addBefore。
删除：removeFirst, removeLast, remove。
替换：replace。
查找：first, last, get。


channelPiple负责为每个新添加的handler分配一个eventExecutor。如果你调用了带grop参数的方法添加handler ，channelPiple会从group中取出一个eventExecutor分配给这个handler, 这时handler中的回调方法会在这个eventExecutor线程中执行，否则channelPiple会把channel的eventLoop当成eventExecutor分配给这个handler，这时这个handler的回调方法会在eventLoop的线程中执行。这个两者有什么不同呢？如果没你没有给handler指定group，它将会和channel的I/O操作共享线程资源，它能得到多少线程资源取决于eventLoop的ioRatio属性的设置，执行时间过长的handler的回调方法会影响I/O操作。如果指定了group，handler的回调方法和channel的I/O操作将会被隔离到不同的线程中。在高并发情况下，强烈建议为不同功能的handler指定不同的group。

每个channel实例在创建的时候，它自己负责创建一个channelPiple实例。随后这个channel会被注册到一个eventLoop中，eventLoop负责处理channel上触发的I/O事件，把I/O事件转换成对channel.unsafe方法的调用。unsafe负责做实际的I/O操作，根据需要调用channelPiple触发事件。channelPiple依次调用合适的handler处理事件。

这里的"依次”和“合适”的含义是:
如果是inbound事件，会从头到尾按顺序调用双向链表上的ChannelInboundHandler类型的handler。
如果是outbound事件，会从尾到头按顺序调用双向链表上的ChannelOutboundHandler类型的handler。

　　piplePile确保一个handler调用始终在一个唯一的eventExecutor中，这个eventExecutor可能是channel的eventLoop，也可能是从用户指定的eventExecutorGroup中分配到的一个executor。


---------------------------------------------------------------------------------------------------------------------
参考
https://www.cnblogs.com/JAYIT/p/8241634.html
http://www.voidcn.com/article/p-rzokhbzl-zh.html
https://blog.csdn.net/zhangjunli/article/details/89382006



Netty的高性能及NIO的epoll空轮询bug


Selector BUG出现的原因
若Selector的轮询结果为空，也没有wakeup或新消息处理，则发生空轮询，CPU使用率100%，

Netty的解决办法
1、对Selector的select操作周期进行统计，每完成一次空的select操作进行一次计数，
2、若在某个周期内连续发生N次空轮询，则触发了epoll死循环bug。
3、重建Selector，判断是否是其他线程发起的重建请求，若不是则将原SocketChannel从旧的Selector上去除注册，重新注册到新的Selector上，并将原来的Selector关闭。

Netty的解决策略：
对Selector的select操作周期进行统计。
每完成一次空的select操作进行一次计数。
在某个周期内如果连续N次空轮询，则说明触发了JDK NIO的epoll死循环bug。
创建新的Selector，将出现bug的Selector上的channel重新注册到新的Selector上。
关闭bug的Selector，使用新的Selector进行替换。

---------------------------------------------------------------------------------------------------------------------

参考
https://blog.csdn.net/j080624/article/details/87209637


TCP粘包/拆包与Netty解决方案

粘包问题的解决策略

由于底层的TCP无法理解上层的业务数据，所以在底层是无法保证数据包不被拆分和重组的，这个问题只能通过上层的应用协议栈设计来解决，根据业界的主流协议的解决方案，可以归纳如下。
(1) 消息定长，例如每个报文的大小为固定长度200字节，如果不够，空位补空格；
(2) 在包尾增加回车换行符进行分割，例如FTP协议；
(3) 将消息分为消息头和消息体，消息头中包含表示消息总长度(或者消息体长度)的字段，通常设计思路为消息头的第一个字段使用int32来表示消息的总长度；
(4) 更复杂的应用层协议。


Netty半包解码器解决TCP粘包/拆包问题
为了解决TCP粘包/拆包导致的半包读写问题，Netty默认提供了多种编解码器用于处理半包，使其解决TCP粘包问题变得非常容易，主要有：

LineBasedFrameDecoder  回车换行解码器
DelimiterBasedFrameDecoder（添加特殊分隔符报文来分包，用户可以指定消息结束的分隔符，回车换行解码器实际上是一种特殊的
FixedLengthFrameDecoder（使用定长的报文来分包）
LengthFieldBasedFrameDecoder  大多数的协议（私有或者公有），协议头中会携带长度字段，用于标识消息体或者整包消息的长度，例如SMPP、HTTP协议等。由于基于长度解码需求的通用性，以及为了降低用户的协议开发难度，Netty提供了LengthFieldBasedFrameDecoder，自动屏蔽TCP底层的拆包和粘包问题，只需要传入正确的参数，即可轻松解决“读半包“问题。



---------------------------------------------------------------------------------------------------------------------

java nio的selector  和linux的epoll select

https://www.cnblogs.com/jukan/p/5272257.html
http://blog.csdn.net/u010853261/article/details/53464475
选择器的创建
当调用Selector.open()时，选择器通过专门的工厂SelectorProvider来创建Selector的实现，SelectorProvider屏蔽了不同操作系统及版本创建实现的差异性。
public static Selector open() throws IOException {
    return SelectorProvider.provider().openSelector();
}
这里系统默认的provider在不同系统上是不一样的，在MacOSX上是默认的KQueueSelectorProvider
因为SelectorProvider本身为一个抽象类，通过调用provider()提供对应的Provider实现，如PollSelectorProvider、EPollSelectorProvider
这是linux操作系统下的DefaultSelectorProvider的实现，可以看到，如果内核版本>=2.6则，具体的SelectorProvider为EPollSelectorProvider，否则为默认的PollSelectorProvider
结合上文，可以猜测一下EPollSelectorProvider提供的Selector肯定是与内核epoll有关的，PollSelectorProvider提供的Selector肯定是与poll有关的。

linux下select/poll/epoll机制的比较
https://www.cnblogs.com/zhaodahai/p/6831456.html
FD：文件描述符（file descriptor）

epoll的优点：
1、没有最大并发连接的限制，能打开的FD的上限远大于1024（1G的内存上能监听约10万个端口）；
2、效率提升，不是轮询的方式，不会随着FD数目的增加效率下降。只有活跃可用的FD才会调用callback函数；
即Epoll最大的优点就在于它只管你“活跃”的连接，而跟连接总数无关，因此在实际的网络环境中，Epoll的效率就会远远高于select和poll。
3、 内存拷贝，利用mmap()文件映射内存加速与内核空间的消息传递；即epoll使用mmap减少复制开销。


http://blog.csdn.net/shallwake/article/details/5265287
那么，为什么epoll,kqueue比select高级？ 
答案是，他们无轮询。因为他们用callback取代了。

---------------------------------------------------------------------------------------------------------------------
参考
https://blog.csdn.net/gaowenhui2008/article/details/55044704


Java异步NIO框架Netty实现高性能高并发

netty高性能
传输：异步非阻塞通信、零拷贝（直接内存和transferTo）、灵活的TCP参数配置能力（option参数的设置）
协议：多种序列化协议，Protobuf的支持、Thrift的
线程：高效的Reactor线程模型、线程池、无锁化的串行设计理念：Netty的NioEventLoop是单线程的



高性能的三个主题
1) 传输：用什么样的通道将数据发送给对方，BIO、NIO或者AIO，IO模型在很大程度上决定了框架的性能。
2) 协议：采用什么样的通信协议，HTTP或者内部私有协议。协议的选择不同，性能模型也不同。相比于公有协议，内部私有协议的性能通常可以被设计的更优。
3) 线程：数据报如何读取？读取之后的编解码在哪个线程进行，编解码后的消息如何派发，Reactor线程模型的不同，对性能的影响也非常大。

Netty高性能之道：
2.2.1. 异步非阻塞通信：多路复用器Selector，读写操作都是非阻塞的，由于Netty采用了异步通信模式，一个IO线程可以并发处理N个客户端连接和读写操作，这从根本上解决了传统同步阻塞IO一连接一线程模型，架构的性能、弹性伸缩能力和可靠性都得到了极大的提升。

2.2.2. 零拷贝
1) Netty的接收和发送ByteBuffer采用DIRECT BUFFERS，使用堆外直接内存进行Socket读写，不需要进行字节缓冲区的二次拷贝。
2) Netty提供了组合Buffer对象，可以聚合多个ByteBuffer对象，用户可以像操作一个Buffer那样方便的对组合Buffer进行操作，避免了传统通过内存拷贝的方式将几个小Buffer合并成一个大的Buffer。
3) Netty的文件传输采用了transferTo方法，它可以直接将文件缓冲区的数据发送到目标Channel，避免了传统通过循环write方式导致的内存拷贝问题。

2.2.3. 内存池
随着JVM虚拟机和JIT即时编译技术的发展，对象的分配和回收是个非常轻量级的工作。但是对于缓冲区Buffer，情况却稍有不同，特别是对于堆外直接内存的分配和回收，是一件耗时的操作。为了尽量重用缓冲区，Netty提供了基于内存池的缓冲区重用机制。


2.2.4. 高效的Reactor线程模型，下面三种都支持
常用的Reactor线程模型有三种，分别如下：
1) Reactor单线程模型；
2) Reactor多线程模型；
3) 主从Reactor多线程模型

2.2.5. 无锁化的串行设计理念：Netty的NioEventLoop是单线程的
Netty的NioEventLoop读取到消息之后，直接调用ChannelPipeline的fireChannelRead(Object msg)，只要用户不主动切换线程，一直会由NioEventLoop调用到用户的Handler，期间不进行线程切换，这种串行化处理方式避免了多线程操作导致的锁的竞争，从性能角度看是最优的。


2.2.6. 高效的并发编程

Netty的高效并发编程主要体现在如下几点：
1) volatile的大量、正确使用;
2) CAS和原子类的广泛使用；
3) 线程安全容器的使用；
4) 通过读写锁提升并发性能。


2.2.7. 高性能的序列化框架
影响序列化性能的关键因素总结如下：
1) 序列化后的码流大小（网络带宽的占用）；
2) 序列化&反序列化的性能（CPU资源占用）；
3) 是否支持跨语言（异构系统的对接和开发语言切换）。
Netty默认提供了对Google Protobuf的支持，通过扩展Netty的编解码接口，用户可以实现其它的高性能序列化框架，例如Thrift的压缩二进制编解码框架。


2.2.8. 灵活的TCP参数配置能力
option参数的设置


---------------------------------------------------------------------------------------------------------------------

Java序列化和Hessian序列化的区别

Hessian本身即是基于Http的RPC实现



在远程调用中，需要把参数和返回值通过网络传输，这个使用就要用到序列化将对象转变成字节流，从一端到另一端之后再反序列化回来变成对象。这里就简单讲讲Java序列化和hessian序列化的区别。

首先，hessian序列化比Java序列化高效很多，而且生成的字节流也要短很多。但相对来说没有Java序列化可靠，而且也不如Java序列化支持的全面。而之所以会出现这样的区别，则要从它们的实现方式来看。

先说Java序列化，具体工作原理就不说了，Java序列化会把要序列化的对象类的元数据和业务数据全部序列化成字节流，而且是把整个继承关系上的东西全部序列化了。它序列化出来的字节流是对那个对象结构到内容的完全描述，包含所有的信息，因此效率较低而且字节流比较大。但是由于确实是序列化了所有内容，所以可以说什么都可以传输，因此也更可用和可靠。

而Hessian序列化，它的实现机制是着重于数据，附带简单的类型信息的方法。就像Integer a = 1，hessian会序列化成I 1这样的流，I表示int or Integer，1就是数据内容。而对于复杂对象，通过Java的反射机制，hessian把对象所有的属性当成一个Map来序列化，产生类似M className propertyName1 I 1 propertyName S stringValue（大概如此，确切的忘了）这样的流，包含了基本的类型描述和数据内容。而在序列化过程中，如果一个对象之前出现过，hessian会直接插入一个R index这样的块来表示一个引用位置，从而省去再次序列化和反序列化的时间。这样做的代价就是hessian需要对不同的类型进行不同的处理（因此hessian直接偷懒不支持short），而且遇到某些特殊对象还要做特殊的处理（比如StackTraceElement）。而且同时因为并没有深入到实现内部去进行序列化，所以在某些场合会发生一定的不一致，比如通过Collections.synchronizedMap得到的map。


---------------------------------------------------------------------------------------------------------------------
https://www.cnblogs.com/xys1228/p/6088805.html
https://blog.csdn.net/baiye_xing/article/details/73351252
http://www.pianshen.com/article/5394293329/

Netty零拷贝：

OS层面零拷贝
netty层面零拷贝



即所谓的 Zero-copy, 就是在操作数据时, 不需要将数据 buffer 从一个内存区域拷贝到另一个内存区域. 因为少了一次内存的拷贝, 因此 CPU 的效率就得到的提升.

在 OS 层面上的 Zero-copy 通常指避免在 用户态(User-space) 与 内核态(Kernel-space) 之间来回拷贝数据. 例如 Linux 提供的 mmap 系统调用, 它可以将一段用户空间内存映射到内核空间, 当映射成功后, 用户对这段内存区域的修改可以直接反映到内核空间; 同样地, 内核空间对这段区域的修改也直接反映用户空间. 正因为有这样的映射关系, 我们就不需要在 用户态(User-space) 与 内核态(Kernel-space) 之间拷贝数据, 提高了数据传输的效率.

而需要注意的是, Netty 中的 Zero-copy 与上面我们所提到到 OS 层面上的 Zero-copy 不太一样, Netty的 Zero-coyp 完全是在用户态(Java 层面)的, 它的 Zero-copy 的更多的是偏向于 优化数据操作 这样的概念.

Netty 的 Zero-copy 体现在如下几个个方面:合并、分解、包装、tranferTo传输
1、Netty 提供了 CompositeByteBuf 类, 它可以将多个 ByteBuf 合并为一个逻辑上的 ByteBuf, 避免了各个 ByteBuf 之间的拷贝.
2、通过 wrap 操作, 我们可以将 byte[] 数组、ByteBuf、ByteBuffer等包装成一个 Netty ByteBuf 对象, 进而避免了拷贝操作.
3、ByteBuf 支持 slice 操作, 因此可以将 ByteBuf 分解为多个共享同一个存储区域的 ByteBuf, 避免了内存的拷贝.
4、通过 FileRegion 包装的FileChannel.tranferTo 实现文件传输, 可以直接将文件缓冲区的数据发送到目标 Channel, 避免了传统通过循环 write 方式导致的内存拷贝问题.





---------------------------------------------------------------------------------------------------------------------
Netty内存管理：堆外内存池



通过堆外内存的方式，避免了频繁的GC，但是带来了另外一个问题堆外内存创建的效率十分的低，所以频繁创建堆外内存更加糟糕。基于上述原因，Netty最终设计了一个堆外内存池，申请了一大块内存空间，然后对这块内存空间提供管理接口，让应用层不需要关注内存操作，能够直接拿到相关数据。













---------------------------------------------------------------------------------------------------------------------


---------------------------------------------------------------------------------------------------------------------


---------------------------------------------------------------------------------------------------------------------
