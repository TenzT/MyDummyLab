package io.nio;

/*
 * 
 */
public class ServerNIO {
	private static int DEFAULT_PORT = 12345;
	private static ServerHandlerNIO serverHandlerNIO;
	public static void start(){
		start(DEFAULT_PORT);
	}
	public static synchronized void start(int port){
		if(serverHandlerNIO!=null)
            serverHandlerNIO.stop();
        serverHandlerNIO = new ServerHandlerNIO(port);
		new Thread(serverHandlerNIO,"Server").start();	// 启动一个服务器
	}
	public static void main(String[] args){
		start();
	}
}
