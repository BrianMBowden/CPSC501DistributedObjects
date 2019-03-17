package receiver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

	private static String HOSTNAME = "127.0.0.1";
	private static int PORT = 6666;
	private DataOutputStream out;
	private DataInputStream in;
	private Socket serverSocket;
	private Socket clientSocket;
	private BufferedReader standardInput;
	
	public Client(){
		standardInput = new BufferedReader(new InputStreamReader(System.in));
		
	}
	
	public void connectToServer(){
		
		// connect to socket on PORT
		try {
			serverSocket = new Socket(InetAddress.getByName(HOSTNAME), PORT);
		} catch (IOException e) {
			System.out.println("Could not connect to server");
			System.exit(-1);
		}
		
		// get input/output streams from server
		try {
			this.out = new DataOutputStream(serverSocket.getOutputStream());
			this.in = new DataInputStream(serverSocket.getInputStream());
		} catch (IOException e) {
			System.out.println("Could not get input/output streams from server");
			System.exit(-1);
		}
		
	}
	
	public void sendQuit(){
		try {
			getOut().writeInt(-1);
		} catch (IOException e) {
			System.out.println("Could not send 'quit' message");
		}
	}
	
	private DataOutputStream getOut(){return this.out;}
}
