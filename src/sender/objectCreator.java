/* ========================================================================================
 * Author: Brian Bowden
 * ID: 10060818
 * Due Date: March 22, 2019
 * Class: CPSC501 T03
 * ========================================================================================
 * sender.objectCreator.java
 * 
 * Handles user input for creation of arbitrary objects
 * 
 * 
 * ========================================================================================
 */
package sender;

import java.util.Scanner;

public class objectCreator {
	
	private Scanner in;
	
	public objectCreator() {
		in = new Scanner(System.in);
	}
	
	public void menuPrompt() {
		System.out.println("=========================================================================================");
		System.out.println("Welcome to the object creator!");
		System.out.println("There are several objects we can create, please make your selection from the menu below:");
		System.out.println("1: Phone (Object Reference)");
		System.out.println("2: Samsung (Primitive Field + Collection Class)");
		System.out.println("3: Apple (Primitive Fields)");
		System.out.println("4: Contact List (Object Array + Primitive Array)");
		System.out.println("5: quit");
		System.out.println("Make your selection by entering the number associated with the object.");
	}
	
	public void exitPrompt() {
		System.out.println("Thank you for using the object creator!");
		System.out.println("Bye!");
		System.out.println("=========================================================================================");
	}
	
	public void flush(){
		in.nextLine();
	}
	
	public int userChoice(){
		int choice;
		while (true){
			if (in.hasNextInt()){
				choice = in.nextInt();
				if (choice > 5 || choice < 1){
					choice = 0;
					System.out.println("Please re-enter integer");
					continue;
				}
				else {
					break;
				}
			}
			else {
				System.out.println("invalid entry");
				in.nextLine();
				continue;
			}
		}
		return choice;
	}
	
	public long userInt(String instruction){
		long number;
		System.out.println("Please enter a " + instruction + ":");
		while (true){
			if(in.hasNextLong()){
				number = in.nextLong();
				break;
			}
			else {
				System.out.println("invalid integer");
				in.nextLine();
				continue;
			}
		}

		return number;
	}
	
	public long[] intArray(String target, String instruction){
		System.out.println("Please enter number of entries in " + target + ":");
		int length = (int)userInt("number");
		long[] arr = new long[length];
		for (int i = 0; i < length; i++){
			arr[i] = userInt(instruction);
			flush();
		}
		return arr;
	}
	
	public String userString(String instruction){
		System.out.println("Please enter a " + instruction + ":");
		String string = in.nextLine();
		System.out.println(string);
		return string;
	}
	
	public boolean userBool(String instruction){
		System.out.println("Please " + instruction + " t/f (true/false)");
		boolean ret;
		flush();
		String str = in.nextLine();
		return(str.compareTo("t") == 0 
				|| str.compareTo("true") == 0 
				|| str.compareTo("True") == 0);
	}
	
	public String[] userStringArr(String target, String instruction){
		String[] strings;
		System.out.println("Please enter number of entries in " + target + ":");
		int size = (int)userInt("number");
		flush();
		strings = new String[size];
		for(int i = 0; i < size; i++){
			strings[i] = userString(instruction);	
		}
		return strings;
	}
}
