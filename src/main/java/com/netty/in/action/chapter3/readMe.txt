Bootstrap ：通过简单配置来设置或引导程序的一个重要类
Handlers来处理特定的event和设置netty的事件
事件可以描述成一个非常通用的方法，因为你可以自定义一个
handler,用来将Object转成byte[]或将byte[]转成Object；也可以定义个handler处理抛出的异常


你会经常编写一个实现ChannelInboundHandler的类，ChannelInboundHandler是用来接收消息，当有消息过来时，你可以决定如何处理。当程序
需要返回消息时可以在ChannelInboundHandler里write/flush数据。可以认为应用程序的业务逻辑都是在ChannelInboundHandler中来处理的，业务罗的
生命周期在ChannelInboundHandler中。

Netty连接客户端端或绑定服务器需要知道如何发送或接收消息，这是通过不同类型的handlers来做的，多个Handlers是怎么配置的？Netty提供了
ChannelInitializer类用来配置Handlers。ChannelInitializer是通过ChannelPipeline来添加ChannelHandler的，如发送和接收消息，这些Handlers将确定
发的是什么消息。ChannelInitializer自身也是一个ChannelHandler，在添加完其他的handlers之后会自动从ChannelPipeline中删除自己。

第一次运行报错
"C:\Program Files\Java\jdk1.8.0_101\bin\java" "-javaagent:G:\IntelliJ IDEA 2017.1.5\lib\idea_rt.jar=64982:G:\IntelliJ IDEA 2017.1.5\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_101\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_101\jre\lib\rt.jar;F:\mydemo\j2se\target\classes;D:\maven\repository\junit\junit\4.12\junit-4.12.jar;D:\maven\repository\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar;D:\maven\repository\io\netty\netty-all\4.1.25.Final\netty-all-4.1.25.Final.jar" com.netty.in.action.chapter2.EchoClient
六月 08, 2018 11:41:32 上午 io.netty.channel.ChannelInitializer exceptionCaught
警告: Failed to initialize a channel. Closing: [id: 0xaa5a3bb4]
java.lang.ClassCastException: io.netty.channel.socket.nio.NioServerSocketChannel cannot be cast to io.netty.channel.socket.SocketChannel
	at com.netty.in.action.chapter2.EchoClient$1.initChannel(EchoClient.java:34)
	at io.netty.channel.ChannelInitializer.initChannel(ChannelInitializer.java:113)
	at io.netty.channel.ChannelInitializer.handlerAdded(ChannelInitializer.java:105)
	at io.netty.channel.DefaultChannelPipeline.callHandlerAdded0(DefaultChannelPipeline.java:637)
	at io.netty.channel.DefaultChannelPipeline.access$000(DefaultChannelPipeline.java:46)
	at io.netty.channel.DefaultChannelPipeline$PendingHandlerAddedTask.execute(DefaultChannelPipeline.java:1487)
	at io.netty.channel.DefaultChannelPipeline.callHandlerAddedForAllHandlers(DefaultChannelPipeline.java:1161)
	at io.netty.channel.DefaultChannelPipeline.invokeHandlerAddedIfNeeded(DefaultChannelPipeline.java:686)
	at io.netty.channel.AbstractChannel$AbstractUnsafe.register0(AbstractChannel.java:510)
	at io.netty.channel.AbstractChannel$AbstractUnsafe.access$200(AbstractChannel.java:423)
	at io.netty.channel.AbstractChannel$AbstractUnsafe$1.run(AbstractChannel.java:482)
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:163)
	at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:404)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:465)
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:884)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.run(Thread.java:745)

java.nio.channels.ClosedChannelException
	at io.netty.channel.AbstractChannel$AbstractUnsafe.ensureOpen(...)(Unknown Source)

	原因：