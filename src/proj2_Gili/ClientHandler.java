package proj2_Gili;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

	// Variables definition
	private String message = "";
	private int messagenum;
	private Socket connection;
	
	/*
	  Constructor to get the connection in order to handle the client.
	  @param connection Socket to handle client
	 */
	public ClientHandler(Socket connection){
		this.connection = connection;
	}
	
	/*
	  Run method trys to create an output and input stream using the Socket connection passed into the constructor.
	  The input is read and sent back to the client with additional information.
	  When the client sends "stop server" the output, input and connection are all closed. 
	 */
	@Override
	public void run() {
		try(
				ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());			
				ObjectInputStream input = new ObjectInputStream(connection.getInputStream())
		){
			do {
				message = (String) input.readObject();
				output.writeObject(messagenum++ + " -Output> " + message);
				output.flush();
			} while (!message.contains("stop server"));
				
		} catch (IOException exception) {
			System.out.println(exception.getMessage());
		} catch (ClassNotFoundException exception) {
			System.out.println(exception.getMessage());
		} finally {
			try {if (connection != null) {connection.close();}} catch (IOException ex) {/* to do */}
		}
	}
}