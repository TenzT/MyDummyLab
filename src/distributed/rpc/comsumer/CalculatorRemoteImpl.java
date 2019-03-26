package distributed.rpc.comsumer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import distributed.rpc.Calculator;
import distributed.rpc.request.CalculateRpcRequest;

public class CalculatorRemoteImpl implements Calculator{
    public static final int PORT = 9090;
    private static Logger log = LoggerFactory.getLogger(CalculatorRemoteImpl.class);
	
	
	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		List<String> addressList = lookupProviders("Calculator,add");
		String address = chooseTarget(addressList);
		Socket socket = null;
		ObjectOutputStream objectOutputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			socket = new Socket(address, PORT);
			// 将请求序列化
			CalculateRpcRequest calculateRpcRequest = generateRequest("add", a, b);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			
			// 将请求发给服务提供方
			objectOutputStream.writeObject(calculateRpcRequest);
			
			// 阻塞直到拿到拿到响应
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			Object response = objectInputStream.readObject();
			
			log.info("response is {}", response);
			
			if (response instanceof Integer) {
				return (Integer) response;
			} else {
				throw new InternalError();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				
		return 0;
	}
	
	public static List<String> lookupProviders(String name) {
		List<String> strings = new ArrayList<>();
		strings.add("127.0.0.1");
		return strings;
	}
	
	private String chooseTarget(List<String> providers) {
		if (providers == null || providers.size()==0) {
			throw new IllegalArgumentException();
		}
		return providers.get(0);
	}
	
	private CalculateRpcRequest generateRequest(String method, int a, int b) {
		CalculateRpcRequest calculateRpcRequest = new CalculateRpcRequest();
		calculateRpcRequest.setMethod(method);
		calculateRpcRequest.setA(a);
		calculateRpcRequest.setB(b);
		return calculateRpcRequest;
	}
}
