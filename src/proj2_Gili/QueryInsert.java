package proj2_Gili;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QueryInsert implements Query{

	// variables
	private String name;
	private double price;
	private int amount;
	private String queryType;
	
	// 
	public QueryInsert(String name, double price, int amount) {
		this.name =name;
		this.price =price;
		this.amount =amount;
		queryType = "Insert";
	}
	
	@SuppressWarnings("unchecked")
	public String toJson() {
		JSONObject jo = new JSONObject();
		jo.put("type", queryType);
		jo.put("amount", amount);
		jo.put("price", price);
		jo.put("name", name);
		return jo.toJSONString();
	}
	
	public static QueryInsert fromJson(String json) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject) parser.parse(json);
		String name = (String) jo.get("name");
		double price = (double) jo.get("price");
		int amount = (int) jo.get("amount");
		QueryInsert qin = new QueryInsert(name,price,amount);
		return qin;
	}
}
