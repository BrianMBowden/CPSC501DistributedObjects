package sender;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
		while(!shutdown){
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Could not establish connection with Client");
				System.exit(-1);
			}
		}
		kill();
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
}
