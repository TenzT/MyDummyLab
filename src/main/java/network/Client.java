package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
	private int port;
	
	public Client(int port) {
		// TODO Auto-generated constructor stub
		this.port = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
