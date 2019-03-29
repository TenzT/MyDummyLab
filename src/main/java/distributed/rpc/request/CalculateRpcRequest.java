package distributed.rpc.request;

import java.io.Serializable;

/*
 * 这个类相当于中间用于传输类、方法和参数的，因此要求可序列化
 */
public class CalculateRpcRequest implements Serializable{
	// 反序列化是依赖serialVersionUID来进行版本验证的，目的是不希望通过编译来划分软件版本，因此显示定义一个版本UID
	private static final long serialVersionUID = 3492039973454717583L;
	
	private String method;	// 方法名
	private int a;			// 第一个参数
	private int b;			// 第二个参数
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return "CalculateRpcRequest [method=" + method + ", a=" + a + ", b=" + b + "]";
	}
	
	

}
