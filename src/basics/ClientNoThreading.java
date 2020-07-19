package basics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientNoThreading {
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		
		//Create a connection, this will pause the program to connect to server on port
		Socket serverConnection = new Socket("127.0.0.1", 16);
		
		/**
		 * This is what we use to read the messages that get send to us from the server
		 * We put it into a BuffereadReader so we can read the messages at our own pace, with no chance for loosing anything
		 */
		BufferedReader input = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
		
		/**
		 * This is what we use to send messages to the server, putting it into a print stream to easily format the message
		 */
		PrintStream output = new PrintStream(serverConnection.getOutputStream());
		
		/**
		 * This sends a message to the server through the socket
		 */
		output.println("This is a message that will get sent to the server");
		
		/**
		 * This receives a message from the server through the socket
		 * NOTE: this will stop the program until it has a message to return 
		 */
		input.readLine();
		
		/**
		 * Close the connection once we are done using it
		 */
		serverConnection.close();
		
	
	}

}
