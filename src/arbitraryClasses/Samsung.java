package arbitraryClasses;

import java.io.Serializable;
import java.util.Vector;

public class Samsung implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int serialNumber;
	private long version;
	private Vector<String> contactList;
	
	public Samsung(){
		contactList = null;
	}
	
	public Samsung(Vector<String> strings){
		this.contactList = strings;
	}
	
	public void setSerial(int s){this.serialNumber = s;}
	public void setVersion(long v){this.version = v;}
}
