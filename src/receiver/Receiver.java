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
		if (fromSender != null){
			System.out.println(fromSender);
			SAXBuilder builder = new SAXBuilder();
			try {
				Document d = builder.build(new StringReader(fromSender));
				Object o = Deserializer.deserializeObject(d);
				ObjectInspector objInspect = new ObjectInspector();
				objInspect.inspect(o, false);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("NULL found");
		}

	}

}
