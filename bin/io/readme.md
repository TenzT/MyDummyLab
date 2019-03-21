# 本次实验将会简单地使用BIO和NIO进行网络编程，并对比阻塞式IO和多路复用的区别

# 在io.bio包中，可以看到使用BIO的话，服务端每个accept请求都会新建线程去处理

# 在io.nio包中，可以看到使用NIO的话，服务器只会处理Channel，并且由原来的（ServerSocket -accept()->Socket）对应变成(ServerSocketChannel -accept()->SocketChannel)