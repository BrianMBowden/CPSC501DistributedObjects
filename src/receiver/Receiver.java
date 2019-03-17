package receiver;

public class Receiver {
	
	public static Client client;

	public static void main(String[] args) {
	
		client = new Client();
		client.connectToServer();

	}

}
