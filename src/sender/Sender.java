package sender;

import java.util.Vector;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import arbitraryClasses.*;


public class Sender {

	public static Server server;
	private static int status;
	
	public static void main(String[] args){
		
		server = new Server();
		objectCreator objC = new objectCreator();
		server.startConnection();
		System.out.println("serialization");
		while(true){
			objC.menuPrompt();
			status = objC.userChoice();
			objC.flush();
			if (status == 5){
				server.talk("quit".getBytes());
				break;
			}
			else if (status == 4){ // Contact list
				ContactList cList = new ContactList();
				cList.setContacts(objC.userStringArr());
				cList.setNumbers(objC.intArray());
				sendXML(cList);
			}
			else if (status == 3){ // Apple
				Apple apple = new Apple();
				apple.setSerial((int)objC.userInt());
				apple.setActive(objC.userBool());
				apple.setGSM((short)objC.userInt());
				sendXML(apple);
			}
			else if (status == 2){ // Samsung
				Samsung samsung = new Samsung();
				samsung.setSerial((int)objC.userInt());
				samsung.setVersion(objC.userInt());
				sendXML(samsung);
			}
			else if (status == 1){ // Phone 
				Phone phone = new Phone();
				phone.setName(objC.userString());
				sendXML(phone);
			}
			else {
				System.out.println("Bad input somewhere, exiting");
				System.exit(-1);
			}
		}
		objC.exitPrompt();
	}
	
	private static void sendXML(Object obj){
		try {
			Document d = Serializer.serialize(obj);
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
