package threadingExamplesClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Lets take a look at how a client class would be set up professionally, with a thread to handle incoming messages
 * @author Ben Shabowski
 *
 */
public class ClientThreaded extends Object {
	
	private Socket serverConnection;
	private PrintStream output;
	
	public ClientThreaded() throws UnknownHostException, IOException {
		//Create a connection, this will pause the program to connect to server on port
		serverConnection = new Socket("127.0.0.1", 16);		
		
		/**
		 * This is what we use to read the messages that get send to us from the server
		 * We put it into a BuffereadReader so we can read the messages at our own pace, with no chance for loosing anything
		 */
		BufferedReader input = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
		
		/**
		 * This is what we use to send messages to the server, putting it into a print stream to easily format the message
		 */
		output = new PrintStream(serverConnection.getOutputStream());
		
		/**
		 * So now what? GOOD QUESTION
		 * generally speaking, input gets thrown into a new thread.
		 * This is to let the regular program run until a message comes in. 
		 * Since its in its own thread, the message can be handled separately
		 * without interfering with the rest of the program.
		 */
	
		/**
		 * This is a quick way to start the incoming messages thread.
		 * NOTE: We don't need to store the thread in an object anywhere since 
		 * we do not need to reference it anywhere else, and it can finish and exit
		 * cleanly without intervention from the main thread.
		 */
		new IncomingMessages(input).start();
		
	}
	
	public void moveForward() {
		
	}
	
	/**
	 * This is a method that most client classes have. This is what we use to handle incoming messages
	 * from the separate thread. We want to keep as much processing in the original thread as possible to avoid
	 * getting, setting, or creating corrupt data.
	 * @param message
	 */
	public void handleMessage(String message) {
		//TODO parse the message and handle what it is here, here are some examples
		if(message.equals("close connection")) {
			try {
				serverConnection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			
		}
	}
	
	/**
	 * Generally speaking, we also have a send message method
	 * @param message
	 */
	public void sendMessage(String message) {
		output.println(message);
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
