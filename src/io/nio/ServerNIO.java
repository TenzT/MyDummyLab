package io.nio;

/*
 * 
 */
public class ServerNIO {
	private static int DEFAULT_PORT = 12345;
	private static ServerHandlerNIO serverHandlerBIO;
	public static void start(){
		start(DEFAULT_PORT);
	}
	public static synchronized void start(int port){
		if(serverHandlerBIO!=null)
			serverHandlerBIO.stop();
		serverHandlerBIO = new ServerHandlerNIO(port);
		new Thread(serverHandlerBIO,"Server").start();	// 启动一个服务器
	}
	public static void main(String[] args){
		start();
	}
}
