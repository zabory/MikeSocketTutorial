package theadingExamplesServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
	
	private Socket clientConnection;
	private PrintStream output;
	
	public Client(Socket clientConnection) {
		
		this.clientConnection = clientConnection;
		
		try {
			//read from client
			BufferedReader input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
			//write to client
			output = new PrintStream(clientConnection.getOutputStream());
			
			//say no to handling input in the same thread, start a new input thread
			new IncomingMessages(input).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a message to the client
	 * @param message
	 */
	public void sendMessage(String message) {
		output.println(message);
	}
	
	/**
	 * A method to handle the message
	 * @param message
	 */
	public void handleMessage(String message) {
		//TODO this will handle the message
	}
	
	public void closeConnection() {
		try {
			clientConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This is a embedded class that will handle incoming messages
	 * @author Ben Shabowski
	 *
	 */
	class IncomingMessages extends Thread{
		private BufferedReader input;
		
		/**
		 * Generally, to keep with OOP rules, we want to pass through the buffered reader rather than
		 * use it from the class above publicly, this also increases security. 
		 * @param input
		 */
		public IncomingMessages(BufferedReader input) {
			this.input = input;
		}
		
		/**
		 * This is the method that gets called and ran separately in a new thread
		 */
		public void run() {
			//We use this to store the incoming data
			String inputMessage = "";
			
			/**
			 * This is the main bit. Generally speaking, in order for the inputStream
			 * to close gracefully and on the programmers own terms rather than through exceptions
			 * or errors, we setup a special input message that closes exits the while loop and can 
			 * be handled by the class above before all the connections get closed.
			 */
			while(!inputMessage.equals("close connection")) {
				//This gets thrown if the connection gets lost suddenly without the client knowing 
				try {
					inputMessage = input.readLine();
					handleMessage(inputMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
