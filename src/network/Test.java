package network;

// 通过实验说明SocketServer开启后等待accept的过程是会阻塞的
public class Test {
	private static final int PORT = 9527;
	
	public static void main(String[] args) throws InterruptedException {
		// 开启服务端
		new Thread(new Server(PORT)).start();
		
		Thread.sleep(500);
		
		// 开启多个客户端，连接到端口去
		for (int i=0; i < 5; i++) {
			new Thread(new Client(PORT)).start();
			
			Thread.sleep(1000);
		}
		System.exit(0);
	}
}
