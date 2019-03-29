package io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 用BIO处理网络IO请求
 */
public class ServerBIO {
	// 默认的端口
	private static int DEFAULT_PORT = 12345;
	
	// 单例的ServerSocker
	private static ServerSocket server;
	
	// 根据传入的参数设置坚挺端口，如果没有参数调用以下方法并使用默认值
	public static void start() throws IOException {
		// 使用默认值
		start(DEFAULT_PORT);
	}
	
	// 
	public synchronized static void start(int port) throws IOException {
		if (server != null) {
			return;
		}
		try {
			server = new ServerSocket(port);
			System.out.println("服务器已启动，端口号为" + port);
			
			// 无限循环监听客户端连接
			while (true) {
				// 如果没有客户端接入，会阻塞在accept操作上
				Socket socket = server.accept();
				// 当有用户接入时，会执行下面代码
				new Thread(new ServerHandler(socket)).start();
			}
		} finally {
			if (server != null) {
				System.out.println("服务器关闭");
				server.close();
				server = null;
			}
		}
	}
	
}
