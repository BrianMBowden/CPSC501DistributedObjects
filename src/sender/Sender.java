/* ========================================================================================
 * Author: Brian Bowden
 * ID: 10060818
 * Due Date: March 22, 2019
 * Class: CPSC501 T03
 * ========================================================================================
 * sender.Sender.java
 * 
 * Program runner for Sending side of Distributed Object Creator
 * Handles all front-end interaction and XML integration
 * 
 * ========================================================================================
 */
package sender;

import java.util.Arrays;
import java.util.Vector;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
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
				cList.setContacts(objC.userStringArr("Contact List", "contact name"));
				cList.setNumbers(objC.intArray("Contact List", "contact number"));
				sendXML(cList);
			}
			else if (status == 3){ // Apple
				Apple apple = new Apple();
				apple.setSerial((int)objC.userInt("serial number"));
				apple.setActive(objC.userBool("active status"));
				apple.setGSM((short)objC.userInt("gsm number"));
				sendXML(apple);
			}
			else if (status == 2){ // Samsung
				String[] cs = objC.userStringArr("Samsung Contact List","contact name");
				Vector<String> v = new Vector<String>(Arrays.asList(cs));
				Samsung samsung = new Samsung(v);
				samsung.setSerial((int)objC.userInt("serial number"));
				samsung.setVersion(objC.userInt("version number"));
				sendXML(samsung);
			}
			else if (status == 1){ // Phone 
				Phone phone = new Phone();
				phone.setName(objC.userString("Name"));
				sendXML(phone);
			}
			else {
				System.out.println("Bad input somewhere, exiting");
				System.exit(-1);
			}
		}
		objC.exitPrompt();
		server.kill();
	}
	
	private static void sendXML(Object obj){
		try {
			Document d = Serializer.serialize(obj);
			XMLOutputter out = new XMLOutputter();
			out.setFormat(Format.getPrettyFormat());
			out.output(d, System.out);
			String send = out.outputString(d);
			byte[] bArray = send.getBytes();
			//System.out.println(send);
			//System.out.println(send.getBytes().length);
			server.talk(bArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
