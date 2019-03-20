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
	
	public void setSerial(int s){this.serialNumber = s;}
	public void setVersion(long v){this.version = v;}
	public void setContactList(String[] c){
		contactList = new Vector<String>();
		for (int i = 0; i < c.length; i++){
			this.contactList.addElement(c[i]);
		}
	}
}
