package distributed.rpc.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import distributed.rpc.Calculator;
import distributed.rpc.request.CalculateRpcRequest;

public class ProviderApp {
    public static final int PORT = 9090;
    
	private static Logger logger = LoggerFactory.getLogger(ProviderApp.class);
	private Calculator calculator = new CalculatorImpl();
	
	public static void main(String[] args) throws IOException {
        new ProviderApp().run();
    }
	
	private void run() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				try {
					ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
					Object object = objectInputStream.readObject();
					
					logger.info("request is {}", object);	// 会调用对应的toString方法
					
					// 调用服务
					int result = 0;
					// 一定要做类型检验,只有转型之后才能调用对应的方法
					if (object instanceof CalculateRpcRequest) {
						CalculateRpcRequest calculateRpcRequest = (CalculateRpcRequest) object;
						if ("add".equals(calculateRpcRequest.getMethod())) {
							result = calculator.add(calculateRpcRequest.getA(), calculateRpcRequest.getB());	
						} else {
							throw new UnsupportedOperationException();
						}
					}
					
					// 返回结果
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
					objectOutputStream.writeObject(result);
					
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("fail", e);
				} finally {
					socket.close();
				}
			}
		} finally {
			// TODO: handle finally clause
			serverSocket.close();
		}
	}
}
