package io.nio;

import java.util.Scanner;

public class TestNIO {
	//测试主方法
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// 运行服务器
		ServerNIO.start();
		
		Thread.sleep(100);
		// 运行客户端
		ClientNIO.start();
		while(ClientNIO.sendMsg(new Scanner(System.in).nextLine()));
	}
}
