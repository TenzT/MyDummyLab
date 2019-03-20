package io.nio;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/* 根据数据类型不同，提供了响应类型的缓冲区，ByteBuffer、CharBuffer、ShortBuffer等
* buffer的方法：
* 	- get()：读取buffer中的数据
* 	- set()：将数据写入buffer
* Buffer四个核心属性：
* 1. capacity：容量，一旦声明不可改变
* 2. limit：表示可以操作的有效的数据
* 3. position:指向准备操作的位置 position<= limit <= capacity
* 这两者的关系和ArrayList中的size和capacity相似
*/
public class TestBuffer {
	public static void main(String[] args) {
		String test = "ABCDE";
		
		// 1. 分配一个指定大小的缓冲区
		System.out.println("-------after allocation ant put------");
		ByteBuffer buffer = ByteBuffer.allocate(10);
		buffer.put(test.getBytes());
		System.out.println("capacity:" + buffer.capacity());
		System.out.println("limit:" + buffer.limit());
		System.out.println("position:" + buffer.position());
		
		// 2. filp使得从写模式变成读模式
		System.out.println("-------after flip------");
		buffer.flip();	// 说明开启读模式，会将position搬回0，limit搬到原来buffer的位置
		System.out.println("capacity:" + buffer.capacity());
		System.out.println("limit:" + buffer.limit());
		System.out.println("position:" + buffer.position());
		buffer.get(new byte[buffer.limit()]);
		
		// 3. rewind将position搬回到0
		System.out.println("-------after rewind------");
		buffer.flip();	// 说明开启读模式，会将position搬回0，limit不变
		System.out.println("capacity:" + buffer.capacity());
		System.out.println("limit:" + buffer.limit());
		System.out.println("position:" + buffer.position());
		
		// 
		System.out.println("-------after clear------");
		buffer.clear();		// 没有真正清除，只是处于被遗忘状态
		System.out.println("capacity:" + buffer.capacity());
		System.out.println("limit:" + buffer.limit());
		System.out.println("position:" + buffer.position());
		
	}
}
