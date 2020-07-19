package theadingExamplesServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerThreaded {

	//This is where we store our clients. Remember, servers have to deal with multiple clients at once so we use a dynamic storage option
	private ArrayList<Client> clients;
	
	public ServerThreaded() {
		
		//initialize list
		clients = new ArrayList<Client>();
		
		/**
		 * Heres where things get tricky. Remember, we want all incoming connections to get their own thread.
		 * Since all the server socket does is handle incoming connections, it gets its own thread
		 * So lets make that thread
		 */
		new incomingConnectionHandler().start();
	}
	
	public void messageAll(String message) {
		clients.forEach(e -> {
			e.sendMessage(message);
		});
	}
	
	public void messageClient(String name, String message) {
		//TODO message specific client
	}
	
	/**
	 * Add client
	 * @param client
	 */
	public void addClient(Client client) {
		clients.add(client);
	}
	
	/**
	 * Class to handle incoming connections
	 * @author Ben Shabowski
	 *
	 */
	class incomingConnectionHandler extends Thread{
		
		public void run() {
			/**
			 * This creates a server, it will listen on the port passed in
			 */
			try {
				ServerSocket server = new ServerSocket(63265);
				//this can go anywhere, in a method, or become its own thing, but we need something to tell the server when to stop accepting clients
				boolean someBooleanToControlWhenToCloseTheServer = true;
				
				while(someBooleanToControlWhenToCloseTheServer) {
					addClient(new Client(server.accept()));
				}
				
				//close the server
				server.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}
