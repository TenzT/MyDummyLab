package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	private int port;
	private static int numConnection = 0;
	private ServerSocket server;
	
	public Server(int port) {
		// TODO Auto-generated constructor stub
		this.port = port;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("服务器已经开始监听端口" + port + ", 开始阻塞自己直到accept");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Socket socket = server.accept();
				System.out.printf("服务端已经监听到了第 %d 个连接进来了\n", ++numConnection);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
