package sender;

public class Sender {

	public static Server server;
	
	public static void main(String[] args){
		
		server = new Server();
		server.startConnection();
	}
}
