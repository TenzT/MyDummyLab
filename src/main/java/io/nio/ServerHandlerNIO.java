package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import io.Calculator;

/*
 * 服务端处理请求的部分，使用NIO完成
 */
public class ServerHandlerNIO implements Runnable{	
	private Selector selector;	//用来轮询
	private ServerSocketChannel serverSocketChannel;	// 用来接收IO请求
	private volatile boolean started;
	// 构造器，这里
	public ServerHandlerNIO(int port) {
		try {
			// 创建选择器
			selector = Selector.open();	// A new selector is created
			// 打开监听通道，以这种方式获取的话，后面还需要绑定一下Socket地址
			serverSocketChannel = ServerSocketChannel.open();	// A channel is created
			// 如果为true，则此通道将被至于阻塞模式，如果为false，则此通道将为非阻塞
			serverSocketChannel.configureBlocking(false);//开启非阻塞模式
			// 绑定端口到通道的socket上，第二个参数backlog表示等待队列的大小
			serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
			// 监听客户端连接请求（OP_ACCEPT），即将channel注册到selector上
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			// 标记服务器已经开始
			started = true;
			System.out.println("服务器已启动，端口号：" + port);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	public void stop() {
		started = false;
	}
	@Override
	public void run() {
		// 循环遍历selector
		while (started) {
			try {
				// 轮询式的选择获取选择器上已经“准备就绪的事件”
				selector.select(1000);	// 无论是否有读写时间发生，selector每隔1s被唤醒一次
				
				Set<SelectionKey> keys = selector.selectedKeys();	// 选择键
				Iterator<SelectionKey> it = keys.iterator();
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();	// 需要remove，否则事件会一直登记
					try {
						handleInput(key);	// 处理选择键
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								key.channel().close();
							}
						}
					}
				}
			} catch(Throwable t){
				t.printStackTrace();
			}
		}
		// selector 关闭后会自动释放里面管理的资源
		if (selector != null) {
			try {
				selector.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {	// 判断键是否有效
			// 处理新接入的请求消息
			if (key.isAcceptable()) {	// 判断是不是Accept事件准备好了
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				// 通过ServerChannel的accept创建SocketChannel实例（搭建铁路）
				// 完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
				// 相较BIO直接从ServerSocket中获取socket，这里是从ServerSocketChannel中获取 SocketChannel
				SocketChannel sc = ssc.accept();
				// 设置为非阻塞的
				sc.configureBlocking(false);
				// 连接成功后，注册一个监听读事件的socketchannel通道
				sc.register(selector, SelectionKey.OP_READ);
			}
			//读消息
			if(key.isReadable()) {	// 到这里就是真正的读取数据了
				SocketChannel sc = (SocketChannel) key.channel();	// 搭建读铁路
				// 创建ByteBuffer，并开辟一个1M的缓冲区
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				// 从socketchannel中读取请求码流，返回读取到的字节数
				int readBytes = sc.read(buffer);
				//读取到字节，对字节进行编解码
				if (readBytes>0) { 
					// 将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
					buffer.flip();
					//根据缓冲区可读字节数创建字节数组
					byte[] bytes = new byte[buffer.remaining()];
					//将缓冲区可读字节数组复制到新建的数组中
					buffer.get(bytes);
					String expression = new String(bytes,"UTF-8");
					System.out.println("服务器收到消息：" + expression);
					//处理数据
					String result = null;
					try{
						result = Calculator.cal(expression).toString();
					}catch(Exception e){
						result = "计算错误：" + e.getMessage();
					}
					//发送应答消息
					doWrite(sc,result);
				} else if (readBytes<0) {
					key.cancel();	// 要记得取消键
					sc.close();		// 关闭socketchannel，否则会一直监听
				}
			}
		}
	}
	
	// 异步发送应答消息，将答案返回到客户端，记住这个Socket通道是双向的
	private void doWrite(SocketChannel channel,String response) throws IOException {
		//将消息编码为字节数组
		byte[] bytes = response.getBytes();
		//根据数组容量创建ByteBuffer
		ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
		//将字节数组复制到缓冲区
		writeBuffer.put(bytes);
		//flip操作,先flip再传送
		writeBuffer.flip();
		//发送缓冲区的字节数组
		channel.write(writeBuffer);
		//****此处不含处理“写半包”的代码
	}
		
}
