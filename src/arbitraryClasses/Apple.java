package arbitraryClasses;

import java.io.Serializable;

public class Apple implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int serial;
	private boolean active;
	private short gsm;
	
	public Apple(){
	}
	
	public void setSerial(int s){this.serial = s;}
	public void setActive(boolean a){this.active = a;}
	public void setGSM(short g){this.gsm = g;}
}
