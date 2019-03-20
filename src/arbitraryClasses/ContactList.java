package arbitraryClasses;

import java.io.Serializable;

public class ContactList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] contacts;
	private long[] phoneNumbers;
	
	public ContactList(){
	}
	
	public void setContacts(String[] c){this.contacts = c;}
	public void setNumbers(long[] n){this.phoneNumbers = n;}
}
