package proj2_Gili;

import java.io.*;
import java.net.*;

@SuppressWarnings("unused")
public class EchoClient {
		
		// Variables definition
		private Socket connection;
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
		private String serverName;
		public static final String DEFAULT_SERVER_NAME = "localhost";
		private int portNum;
		private ListOfItem loi;
		private Item myItem;
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

		/*
		  Main method accepts parameters for server name, port num.
		  @param args
		 */
		public static void main(String[] args) {
			switch (args.length) {
			case 2:
				(new EchoClient(args[0], Integer.parseInt(args[1]))).runClient();
				break;
			case 1:
				(new EchoClient(DEFAULT_SERVER_NAME, Integer.parseInt(args[0]))).runClient();
				break;
			default:
				(new EchoClient(DEFAULT_SERVER_NAME, EchoServer.DEFAULT_PORT)).runClient();
			}
		}
		
		/*
		  Constructor for EchoClient to get values in-order to create the Socket.
		  @param serverName Value of the server address.
		  @param portNum value of the server port.
		 */
		public EchoClient(String serverName, int portNum) {
			this.serverName = serverName;
			this.portNum = portNum;
		}

		/*
		  Client code block creates a socket as well as an input and output stream.
		  The client waits for user input and sends the message to the server.
		  The servers response is then printed.
		  The client will quit if "stop server" is returned in the server response.
		  in case of "Insert" query, data is saved to db (using xampp)
		 */
		public void runClient() {
			try {
				connection = new Socket(InetAddress.getByName(serverName), portNum);
				output = new ObjectOutputStream(connection.getOutputStream());
				input = new ObjectInputStream(connection.getInputStream());
				out = new PrintWriter(output);
				in = new BufferedReader(new InputStreamReader(input));
				loi = new ListOfItem();
				System.out.println("To Quit, enter 'stop server'");
				do {
					System.out.print("Input> ");
					message = keyboard.readLine();
					
					// find by Id
					if (message.equals("findById")){
						
						// gets the message
						output.writeObject(message);
						output.flush();
						message = (String) input.readObject();
						
						// asks for id
						System.out.println("Please enter ID");
						
						// gets id
						message = keyboard.readLine();
						output.writeObject(message);
						output.flush();

						try {
							
							// performing query
							this.myId = Integer.parseInt(message);
							this.query = new QueryGetById(myId);
							output.writeObject(query.toJson());
							message = (String) input.readObject();
							output.flush();
							
							// prints query
							//System.out.println(message);
							System.out.println(query.toJson());
							
							// prints response
							System.out.println("Response");
							
							try {
								System.out.println(loi.getById(myId).toJSONString());
							}catch(Exception e) {
								message = "No item found";
								output.writeObject(message);
								output.flush();
								System.out.println(message);
							}
									
						}catch(Exception e) {
							message = "Not valid input";
							output.writeObject(message);
							output.flush();
							System.out.println(message);
						}
					}
					
					// find by Prefix
					else if (message.equals("findByPrefix")){
						
						// gets the message
						//message = keyboard.readLine();
						output.writeObject(message);
						output.flush();
						message = (String) input.readObject();
						
						// asks for Prefix
						System.out.println("Please enter Prefix");
						
						// gets Prefix
						message = keyboard.readLine();
						output.writeObject(message);
						output.flush();
						try {
							
							// performing query
							this.myPrefix = message;
							this.query = new QueryGetByPrefix(myPrefix);
							output.writeObject(query.toJson());
							message = (String) input.readObject();
							output.flush();
							
							// prints query
							//System.out.println(message);
							System.out.println(query.toJson());
							
							// prints response
							System.out.println("Response");
							
							try {
								System.out.println(loi.findByPrefix(myPrefix).toJSONString());
							}catch(Exception e) {
								message = "No items found";
								output.writeObject(message);
								output.flush();
								System.out.println(message);
							}
									
						}catch(Exception e) {
							message = "Not valid input";
							output.writeObject(message);
							output.flush();
							System.out.println(message);
						}
					}
					
					// Insert
					else if (message.equals("Insert")){
						
						// gets the message
						output.writeObject(message);
						output.flush();
						message = (String) input.readObject();
						
						// asks for Name
						System.out.println("Please enter name");
						
						// gets Name
						message = keyboard.readLine();
						output.writeObject(message);
						output.flush();
						try {							
							this.myName = message;
							
							// gets Price
							System.out.println("Please enter price");
							message = keyboard.readLine();
							output.writeObject(message);
							output.flush();
							try {
								this.myPrice = Double.parseDouble(message);
							}catch(Exception e) {}
							
							// gets amount
							System.out.println("Please enter Amount");
							message = keyboard.readLine();
							output.writeObject(message);
							output.flush();
							try {
								this.myAmount = Integer.parseInt(message);
							}catch(Exception e) {}
							
							// performing query
							this.query = new QueryInsert(myName, myPrice, myAmount);
							output.writeObject(query.toJson());
							message = (String) input.readObject();
							output.flush();
							
							// prints query
							System.out.println(query.toJson());
							
							// prints response
							System.out.println("Response");
							
							try {
								myItem = new Item(myName, myPrice, myAmount);
								loi.add(myItem);
								System.out.println(myItem.getItemJsonObj().toJSONString());
							}catch(Exception e) {
								message = "No Item added";
								output.writeObject(message);
								output.flush();
								System.out.println(message);
							}
									
						}catch(Exception e) {
							message = "Not valid input";
							output.writeObject(message);
							output.flush();
							System.out.println(message);
						}
					}
					else if(!(message.contains("stop server"))) {
						
						// gets the message
						output.writeObject(message);
						output.flush();
						System.out.println("'"+ message + "'" + " is not ot a query, try again");
					}
				}while (!message.contains("stop server"));
				input.close();
				output.close();
				connection.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}