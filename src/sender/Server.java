/* ========================================================================================
 * Author: Brian Bowden
 * ID: 10060818
 * Due Date: March 22, 2019
 * Class: CPSC501 T03
 * ========================================================================================
 * sender.Server.java
 * 
 * Socket opener and closer. Maintains connection over TCP socket. 
 * 		Says hello to client upon successful connection and sends all output via byte array
 * 		through a DataOutputStream
 * 
 * ========================================================================================
 */
package sender;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private static int PORT = 6666;
	private ServerSocket serverSocket;
	private DataOutputStream out;
	private DataInputStream in;
	private Socket clientSocket;
	private boolean shutdown;
	private int status;
	
	public Server(){
		this.shutdown  = false;
		this.status = 0;
	}
	
	public void startConnection(){
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println("Could not create server port on port: " + PORT);
			System.exit(-1);
		}
		// I was here. need to get socket data
		listen();
		
		
	}
	
	private void listen(){
		int currentStatus = getStatus();
		try {
			clientSocket = serverSocket.accept();
			setIn(clientSocket.getInputStream());
			setOut(clientSocket.getOutputStream());
			getOut().writeUTF("Established Connection");
		} catch (IOException e) {
			System.out.println("Could not establish connection with Client");
			System.exit(-1);
		}
	
	}
	
	public void talk(byte[] bArray){
		System.out.println("Waiting for client");
		
		try {
			getOut().writeInt(bArray.length);
			getOut().write(bArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void kill(){
		try {
			getClientSocket().close();
			getServerSocket().close();
		} catch (IOException e) {
			System.out.println("Could not shutdown socket");
		}
		
	}
	
	private Socket getClientSocket(){return clientSocket;}
	private ServerSocket getServerSocket(){return serverSocket;}
	private void flipShutdown(){this.shutdown = !this.shutdown;}
	private int getStatus(){return this.status;}
	private void setIn(InputStream dIn){this.in = new DataInputStream(dIn);}
	private void setOut(OutputStream dOut){this.out = new DataOutputStream(dOut);}
	public DataInputStream getIn(){return in;}
	public DataOutputStream getOut(){return out;}
}
