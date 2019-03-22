/* ========================================================================================
 * Author: Brian Bowden
 * ID: 10060818
 * Due Date: March 22, 2019
 * Class: CPSC501 T03
 * ========================================================================================
 * receiver.ObjectInspector.java
 * 
 * Reflective object inspector.
 * 
 * ========================================================================================
 */
package receiver;

import java.util.*;
import java.lang.reflect.*;

public class ObjectInspector {
	
	
	public ObjectInspector() { } // no need for a constructor
	
	public void inspect (Object object, boolean recursive){
		
		Class classObject = object.getClass();
		
		String fullName = "";
		fullName += Modifier.toString(getClassModifiers(classObject));
		fullName += " class ";
		fullName += getClassName(classObject);
		if (classObject.getSuperclass() != java.lang.Object.class){
			fullName += " extends ";
			fullName += getSuperclassName(classObject);
			fullName += " ";
		}
		if (classObject.getInterfaces().length > 0){
			fullName += " implements ";
			Class[] interfaces = getInterfaces(classObject);
			for (Class face : interfaces){
				fullName += face.getSimpleName();
				fullName += " ";
			}
		}	
		System.out.println(fullName + "\n");
		
		inspectFields(object, classObject);
		inspectConstructors(classObject);		
		inspectMethods(classObject);
	}
	
	private void inspectFields(Object obj, Class classObj){
		if (classObj.getDeclaredFields().length > 0){
			Field fields[] = classObj.getDeclaredFields();
			for (Field field : fields){
				if (field.getType().isArray()){
					if (!field.isAccessible()){
						field.setAccessible(true);
					}
					try {
						String arr = printArray(field.getType(), field, field.get(obj));
						System.out.println(arr);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				else if (!field.getType().isPrimitive()){
					if (!field.isAccessible()){
						field.setAccessible(true);
					}
					String cls = "";
					Object o;
					cls += Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName() + " ";
					try {
						o = field.get(obj);
						if (o != null){
							inspectFields(o, o.getClass());
							//cls += o.getClass().hashCode();
						}
						else {
							cls += "null";
						}
						System.out.println(cls);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				else { // is primitive
					if (!field.isAccessible()){
						field.setAccessible(true);
					}
					String prim = "";
					Object o;
					prim += Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName() + " ";
					try {
						o = field.get(obj);
						if (o != null){
							prim += o;
						}
						else {
							prim += "null";
						}
						System.out.println(prim);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
	
				}
				
			}
		}

	}
	
	private String printArray(Class fieldClass, Field field, Object arrObject){
		String printedArray = "";
		int arrSize = Array.getLength(arrObject);
		printedArray += Modifier.toString(field.getModifiers()) + " ";
		printedArray += fieldClass.getSimpleName() + " ";
		printedArray += field.getName();
		printedArray += " [" + arrSize + "] ";
		printedArray += "{";
		for(int i = 0; i < arrSize; i++){
			Object o = Array.get(arrObject, i);
			if (o != null){
				if (isPrimitiveWrap(o.getClass())){
					printedArray += o;
				}
				else {
					System.out.println(printedArray);
					inspectFields(o, o.getClass());
					System.out.println("}");
					//printedArray += o.getClass().getSimpleName() + ": " + o.hashCode();
				}
			}
			if (!(arrSize - 1 == i)){
				printedArray += ", ";
			}
		}
		printedArray += "}";
		
		return printedArray;
	}
	
	private boolean isPrimitiveWrap(Class classObj){
		return(    classObj == Boolean.class 
				|| classObj == Integer.class 
				|| classObj == Double.class 
				|| classObj == Byte.class
				|| classObj == Long.class
				|| classObj == Short.class
				|| classObj == Float.class
				|| classObj == Double.class
				|| classObj == Character.class );
	}
	

	
	private void inspectMethods(Class classObj){
		System.out.println();
		if (classObj.getDeclaredMethods().length > 0){

			Method methods[] = classObj.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++){
				methods[i].setAccessible(true);
				// return types
				String returnType = methods[i].getReturnType().getSimpleName();
				
				// parameter types
				String parameters = "( ";
				Class[] params = methods[i].getParameterTypes();
				if (methods[i].getParameterTypes().length > 0){
					for(Class param : params){
						parameters += param.getSimpleName();
						parameters += " ";
					}
				}
				parameters += ")";
				
				// exceptions
				String exceptions = "";
				Class[] exceps = methods[i].getExceptionTypes();
				if (methods[i].getExceptionTypes().length > 0){
					exceptions += " throws ";
					for (Class excep : exceps){
						exceptions += excep.getSimpleName();
						exceptions += " ";
					}
				}
				// modifiers
				String modifiers = Modifier.toString(methods[i].getModifiers());
				try{
					System.out.println(modifiers + " " + returnType + " " + methods[i].getName() + " " + parameters + exceptions);
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	private void inspectConstructors(Class classObj){
		if (classObj.getDeclaredConstructors().length > 0){
			Constructor constructors[] = classObj.getDeclaredConstructors();
			for (Constructor constr : constructors){
				Class[] params = constr.getParameterTypes();
				String parameters = "( ";
				for (Class param : params){
					parameters += param.getSimpleName();
					parameters += " ";
				}
				parameters += ")";
				System.out.println(Modifier.toString(constr.getModifiers()) + " " + constr.getName() + parameters);
			}
		}
	}
	
	private String getClassName(Class objClass){ return objClass.getSimpleName(); }
	private String getSuperclassName(Class objClass) {return objClass.getSuperclass().getSimpleName(); }
	private Class[] getInterfaces(Class objClass) {return objClass.getInterfaces();}
	private int getClassModifiers(Class objClass) {return objClass.getModifiers();}
	
	
	
	
}