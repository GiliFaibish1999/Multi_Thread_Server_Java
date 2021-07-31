package proj2_Gili;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;

@SuppressWarnings("serial")
public class Item implements Serializable{
	
	// Variables definition
	private String name;
	private double price;
	private int amount;
	public static int LastID = 0;
	private int id;
	
	// Item object
	public Item(String name, double price, int amount) throws ClassNotFoundException, SQLException {
		super();
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.id = LastID;
		LastID ++;
		
		// Saving the item in the database after it's creation
		this.saveToDB();
	}

	public static int  getlastID() {	
	return 	LastID;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	// Returns a Json object of this item
	@SuppressWarnings("unchecked")
	public JSONObject getItemJsonObj(){
		JSONObject ijo = new JSONObject();
		ijo.put("amount", this.amount);
		ijo.put("price", this.price);
		ijo.put("name", this.name);
		ijo.put("id", this.id);
		return ijo;
	}
	
	// Saves to database
	public void saveToDB() throws ClassNotFoundException, SQLException{
		String sql = "INSERT INTO `items`(`id`, `name`, `price`, `amount`) VALUES "
				+ "(" +this.id+",'" +this.name+"'," +this.price+ ", " + this.amount+")";
		Statement stm = MyConnection.getConnection().createStatement();
		stm.executeUpdate(sql);
	}
}