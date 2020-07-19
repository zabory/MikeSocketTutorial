package basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNoThreading {
	
	public static void main(String[] args) throws IOException {
		
		/**
		 * This creates a server, it will listen on the port passed in
		 */
		ServerSocket server = new ServerSocket(63265);
		
		/**
		 * This creates a client socket
		 * NOTE: the program will not continue until the server accepts a client
		 */
		Socket client = server.accept();
		
		/**
		 * input and output are the same as regular client
		 */
		BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintStream clientOutput = new PrintStream(client.getOutputStream());
		
		
		/**
		 * Close the server when you are done with it
		 */
		server.close();
		
	}

}
