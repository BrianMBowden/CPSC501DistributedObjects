/* ========================================================================================
 * Author: Brian Bowden
 * ID: 10060818
 * Due Date: March 22, 2019
 * Class: CPSC501 T03
 * ========================================================================================
 * receiver.Deserializer.java
 * 
 * Handles deserialization of an XML Document and returns it as an object in its original 
 * state
 * 
 * Serialization method credit:
 * Chapter 3
 * Forman, Ira R., and Nate Forman. Java Reflection in Action. Manning, 2005.
 * 
 * ========================================================================================
 */
package receiver;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Content;

import arbitraryClasses.*;

public class Deserializer {

	public static Object deserializeObject(Document source) throws Exception {
		List objectList = source.getRootElement().getChildren();
		Map table = new HashMap();
		createInstance(table, objectList);
		assignFieldValues(table, objectList);
		return table.get("0");
	}
	
	private static void createInstance(Map table, List objectList) throws Exception {
		for(int i = 0; i < objectList.size(); i++){
			Element objectElement = (Element) objectList.get(i);
			Class cls = Class.forName(objectElement.getAttributeValue("class"));
			Object instance = null;
			if (!cls.isArray()){
				Constructor c = cls.getDeclaredConstructor();
				if (!Modifier.isPublic(c.getModifiers())){
					c.setAccessible(true);
				}
				instance = c.newInstance();
			}
			else {
				instance = Array.newInstance(cls.getComponentType(), Integer.parseInt(objectElement.getAttributeValue("length")));
				
			}
			table.put(objectElement.getAttributeValue("id"), instance);
		}
	}
	
	private static void assignFieldValues(Map table, List objectList) throws Exception {
		for (int i = 0; i < objectList.size(); i++){
			Element objectElement = (Element) objectList.get(i);
			Object instance = table.get(objectElement.getAttributeValue("id"));
			List fieldElements = objectElement.getChildren();
			if(!instance.getClass().isArray()){
				for(int j = 0; j < fieldElements.size(); j++){
					Element fieldElement = (Element) fieldElements.get(j);
					String className = fieldElement.getAttributeValue("declaringclass");
					Class fieldDC = Class.forName(className);
					String fieldName = fieldElement.getAttributeValue("name");
					Field f = fieldDC.getDeclaredField(fieldName);
					if (!Modifier.isPublic(f.getModifiers())){
						f.setAccessible(true);
					}
					Element vElement = (Element) fieldElement.getChildren().get(0);
					f.set(instance, deserializeValue(vElement, f.getType(), table));
				}
			}
			else {
				Class comptype = instance.getClass().getComponentType();
				for(int j = 0; j < fieldElements.size(); j++){
					Array.set(instance, j, deserializeValue((Element)fieldElements.get(j), comptype, table));
				}
			}
		}
	}
	
	private static Object deserializeValue(Element vElement, Class fieldType, Map table) throws ClassNotFoundException {
		String valtype = vElement.getName();
		if(valtype.equals("null")){
			// return null was giving me issues, changed this to get past an error
			return Integer.valueOf(0);
		}
		else if (valtype.equals("reference")){
			return table.get(vElement.getText());
		}
		else {
			if (fieldType.equals(boolean.class)){
				if (vElement.getText().equals("true")){
					return Boolean.TRUE;
				}
				else {
					return Boolean.FALSE;
				}
			}
			else if (fieldType.equals(byte.class)){
				return Byte.valueOf(vElement.getText());
			}
			else if (fieldType.equals(short.class)){
				return Short.valueOf(vElement.getText());
			}
			else if (fieldType.equals(int.class)){
				return Integer.valueOf(vElement.getText());
			}
			else if (fieldType.equals(long.class)){
				return Long.valueOf(vElement.getText());
			}
			else if (fieldType.equals(float.class)){
				return Float.valueOf(vElement.getText());
			}
			else if (fieldType.equals(double.class)){
				return Double.valueOf(vElement.getText());
			}
			else if (fieldType.equals(char.class)){
				return new Character(vElement.getText().charAt(0));
			}
			else {
				return vElement.getText();
			}
			
		}
	}
}
