package protobuffer;

import com.google.protobuf.InvalidProtocolBufferException;

import protobuffer.PersonProtoBuf.Person;

public class Main {
	
	// 创建实例
	public static Person buildInstance() {
//		Person person = Person.newBuilder().setName("Tenz").setAge(20).build();
		Person.Builder personBuilder = Person.newBuilder();
		personBuilder.setName("Tenz");
		personBuilder.setAge(20);
		
		return personBuilder.build();
	}
	
	// 
	public static void main(String[] args) {
		Person person = buildInstance();
		System.out.println(person);
		
		System.out.println("生成字节流");
		byte[] bs = person.toByteArray();	// 实际上有了字节流就可以通过套接字传输了
		for (byte b : bs) {
			System.out.print(b + " ");
		}

		System.out.println();
		
		System.out.println("从字节流中解析对象");
		Person personOut = null;
		try {
			personOut = Person.parseFrom(bs);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(personOut);
	}
	
}
