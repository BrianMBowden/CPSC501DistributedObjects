/* ========================================================================================
 * Author: Brian Bowden
 * ID: 10060818
 * Due Date: March 22, 2019
 * Class: CPSC501 T03
 * ========================================================================================
 * receiver.Receiver.java
 * 
 * Program runner for Receiving side of Distributed Object Creator
 * Handles all front-end interaction and XML integration
 * 
 * ========================================================================================
 */
package receiver;

import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import arbitraryClasses.*;

public class Receiver {
	
	public static Client client;

	public static void main(String[] args) {
	
		client = new Client();
		client.connectToServer();
		String fromSender = client.waitForInput();
		while (!fromSender.equals("quit")){
			try {
				rebuild(fromSender);
				fromSender = client.waitForInput();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);	
			}
		}
	}

	
	public static void rebuild(String string) throws Exception{
		SAXBuilder builder = new SAXBuilder();
		try {
			Document d = builder.build(new StringReader(string));
			Object o = Deserializer.deserializeObject(d);
			ObjectInspector objInspect = new ObjectInspector();
			objInspect.inspect(o, true);
		} catch (Exception e) {
			throw new Exception();
		}
	}
}
