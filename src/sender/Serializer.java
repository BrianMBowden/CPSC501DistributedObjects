/* ========================================================================================
 * Author: Brian Bowden
 * ID: 10060818
 * Due Date: March 22, 2019
 * Class: CPSC501 T03
 * ========================================================================================
 * sender.Serializer.java
 * 
 * Handles serialization of an object in preparation for an XML Document
 * 
 * Serialization method credit:
 * Chapter 2
 * Forman, Ira R., and Nate Forman. Java Reflection in Action. Manning, 2005.
 *
 * ========================================================================================
 */
package sender;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.lang.reflect.*;

import org.jdom2.*;
import org.jdom2.Content;

public class Serializer {

	
	public Serializer(){
		
	}
	
	public static Document serialize(Object obj) throws Exception{
		return serializeHelper(obj, new Document(new Element("serialized")), new IdentityHashMap());
		
	}
	
	private static Document serializeHelper(Object obj, Document target, Map table) throws Exception {
		
		// Create unique identifier for object to be serialized
		String id = Integer.toString(table.size());
		table.put(obj, id);
		Class objClass = obj.getClass();
		
		// Create an XML element for object
		Element objElement = new Element("object");
		objElement.setAttribute("class", objClass.getName());
		objElement.setAttribute("id", id);
		target.getRootElement().addContent(objElement);
		
		// handle array differently from scalar
		if(!obj.getClass().isArray()){
			// Obtain non-static fields
			Field[] fields = getInstanceVariables(objClass);
			for (Field field : fields){
				// permit access if necessary
				if(!Modifier.isPublic(field.getModifiers())){
					field.setAccessible(true);
				}
				
				// Create new XML elements
				Element fieldElement = new Element("field");
				fieldElement.setAttribute("name", field.getName());
				Class declaringClass = field.getDeclaringClass();
				fieldElement.setAttribute("declaringclass", declaringClass.getName());
				Class fieldType = field.getType();
				Object child = field.get(obj);
				
				if (Modifier.isTransient(field.getModifiers())){
					child = null;
				}
				fieldElement.addContent(serializeVariable(fieldType, child, target, table));
				
				objElement.addContent(fieldElement);
			}
		}
		
		else {
			// Add components of the array
			Class componentType = objClass.getComponentType();
			int length = Array.getLength(obj);
			objElement.setAttribute("length", Integer.toString(length));
			for(int i = 0; i < length; i++){
				objElement.addContent(serializeVariable(componentType, Array.get(obj, i), target, table));
				
			}
		}
		return target;
		
	}
	
	private static Field[] getInstanceVariables(Class cls){
		List accum = new LinkedList();
		while (cls != null){
			Field fields[] = cls.getDeclaredFields();
			for (Field field : fields){
				if(!Modifier.isStatic(field.getModifiers())){
					accum.add(field);
				}
			}
			cls = cls.getSuperclass();
		}
		Field[] retvalue = new Field[accum.size()];
		return (Field[]) accum.toArray(retvalue);
	}
	
	private static Element serializeVariable(Class fieldtype, Object child, Document target, Map table) throws Exception{
		if (child == null){
			return new Element("null");
		}
		else if (!fieldtype.isPrimitive()){
			Element reference = new Element("reference");
			if (table.containsKey(child)){
				reference.setText(table.get(child).toString());
			}
			else {
				reference.setText(Integer.toString(table.size()));
				serializeHelper(child, target, table);
			}
			return reference;
		}
		else {
			Element value = new Element("value");
			value.setText(child.toString());
			return value;
		}
	}
	
}
