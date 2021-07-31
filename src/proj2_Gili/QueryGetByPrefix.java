package proj2_Gili;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QueryGetByPrefix implements Query{

	// variables
	private String prefix;
	private String queryType;
	
	// 
	public QueryGetByPrefix(String prefix) {
		this.prefix =prefix;
		queryType = "findByPrefix";
	}
	
	@SuppressWarnings("unchecked")
	public String toJson() {
		JSONObject jo = new JSONObject();
		jo.put("type", queryType);
		jo.put("prefix", prefix);
		return jo.toJSONString();
	}
	
	public static QueryGetByPrefix fromJson(String json) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject) parser.parse(json);
		String prefix = (String) jo.get("prefix");
		QueryGetByPrefix qgbp = new QueryGetByPrefix(prefix);
		return qgbp;
	}
}