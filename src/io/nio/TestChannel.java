package io.nio;

/*
 * Channel是在DMA上改进的，读写请求数据搬移交给了DMA而不是CPU直接负责，但是DMA会存在总线冲突的问题
 * 通道是一个完全独立的处理器，专门用于IO操作的处理器
 * 
 * 主要实现类：FileChannel、SocketChannel、ServerSocketChannel、DatagramChannel
 * 获取方式：一般使用通道类的open()方法获得
 * 
 * 阻塞式通信和非阻塞式通信是相较于网络通信而言的
 * 首先要来理解Server端会有将数据从内核空间复制到用户空间的过程
 * - 阻塞：传统IO下，服务端会新建线程来处理用户的请求，当数据未准备好（无论是读用户端，还是服务端
 * 		从内核态-->用户空间返回到用户空间），在这期间会一直等待，原来解决的方式只能靠多线程，然而多线程启动和销毁开销大
 * - 非阻塞：引入选择器，轮询所有注册到选择器上的通道，当某个通道上的数据准备好之后，selector才会将任务分配到一个或多个线程上
 * 		即当Channel上未准备好数据时，CPU还能去干其他事
 * 
 * 选择键：通道register时，会传递一个ops函数，用来说明Selector会监听通道上的什么事件
 */
public class TestChannel {
	
}
