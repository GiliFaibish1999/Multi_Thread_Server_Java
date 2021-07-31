package proj2_Gili;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

@SuppressWarnings("unused")
public class EchoServer {

	private Socket connection;
	private ServerSocket server;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private PrintWriter out;
	private BufferedReader in;
	private String message = "";
	private Query query;
	private int myId;
	private String myPrefix;
	private String myName;
	private int myAmount;
	private double myPrice;
	private int messagenum;
	private int portNum;
	private ListOfItem loi;
	private Item myItem;
	public static final int DEFAULT_PORT = 3366;
	public static ExecutorService threadExecutor = Executors.newCachedThreadPool();

	/*
	  Server can be ran using a port defined in parameters or constant DEFAULT_PORT.
	  @param args 
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			(new EchoServer(Integer.parseInt(args[0]))).runServer();
		} else {
			(new EchoServer(DEFAULT_PORT)).runServer();
		}
	}

	/*
	  EchoServer constructor sets the portNum for creating the ServerSocket.
	  @param portNum
	 */
	public EchoServer(int portNum) {
		this.portNum = portNum;
	}

	/*
	  Server code which instantiates the server socket and then constantly waits for connections and executes them on a new thread.
	 */
	public void runServer() {
		try{
			server = new ServerSocket(portNum);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Listening for connections...");
		while(true){
			try{
				connection = server.accept();
				ClientHandler client = new ClientHandler(connection);
				threadExecutor.execute(client);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
}
