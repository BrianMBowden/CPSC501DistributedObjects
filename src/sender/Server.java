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
		} catch (IOException e) {
			System.out.println("Could not establish connection with Client");
			System.exit(-1);
		}
	
	}
	
	public void talk(byte[] bArray){
		System.out.println("Waiting for client");
		
	}
	
	private void kill(){
		try {
			getClientSocket().close();
			getServerSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not shutdown socket");
		}
		
	}
	
	private Socket getClientSocket(){return clientSocket;}
	private ServerSocket getServerSocket(){return serverSocket;}
	private void flipShutdown(){this.shutdown = !this.shutdown;}
	private int getStatus(){return this.status;}
	private void setIn(InputStream dIn){this.in = new DataInputStream(dIn);}
	private void setOut(OutputStream dOut){this.out = new DataOutputStream(dOut);}
	private DataInputStream getIn(){return in;}
	private DataOutputStream getOut(){return out;}
}
