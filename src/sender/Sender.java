package sender;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import arbitraryClasses.*;


public class Sender {

	public static Server server;
	
	public static void main(String[] args){
		
		server = new Server();
		server.startConnection();
		Phone phone = new Phone();
		phone.setName("Brian");
		Samsung samsung = new Samsung();
		samsung.setContactList(new String[]{"Brian", "Omar"});
		samsung.setSerial(123456);
		samsung.setVersion(8888888888888L);
		Apple apple = new Apple();
		apple.setSerial(123456);
		apple.setActive(false);
		apple.setGSM((short)32);
		ContactList cList = new ContactList();
		cList.setContacts(new String[]{"Brian","Omar"});
		cList.setNumbers(new long[]{1234L, 2345L});
		System.out.println("serialization");
		try {
			Document d = Serializer.serialize(cList);
			XMLOutputter out = new XMLOutputter();
			String send = out.outputString(d);
			byte[] bArray = send.getBytes();
			System.out.println(send);
			System.out.println(send.getBytes().length);
			server.talk(bArray);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
	}
}
