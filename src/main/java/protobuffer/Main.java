package protobuffer;

import com.google.protobuf.InvalidProtocolBufferException;

public class Main {
	
	// 创建实例
	public static PersonProtoBuf.Person buildInstance() {
//		Person person = Person.newBuilder().setName("Tenz").setAge(20).build();
		PersonProtoBuf.Person.Builder personBuilder = PersonProtoBuf.Person.newBuilder();
		personBuilder.setName("Tenz");
		personBuilder.setAge(20);
		
		return personBuilder.build();
	}
	
	// 
	public static void main(String[] args) {
        PersonProtoBuf.Person person = buildInstance();
		System.out.println(person);
		
		System.out.println("生成字节流");
		byte[] bs = person.toByteArray();	// 实际上有了字节流就可以通过套接字传输了
		for (byte b : bs) {
			System.out.print(b + " ");
		}

		System.out.println();
		
		System.out.println("从字节流中解析对象");
        PersonProtoBuf.Person personOut = null;
		try {
			personOut = PersonProtoBuf.Person.parseFrom(bs);
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(personOut);
	}
	
}
