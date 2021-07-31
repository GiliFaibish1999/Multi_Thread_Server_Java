package proj2_Gili;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class QueryGetById implements Query{

	// variables
	private int id;
	private String queryType;
	
	// 
	public QueryGetById(int id) {
		this.id =id;
		queryType = "findById";
	}
	
	@SuppressWarnings("unchecked")
	public String toJson() {
		JSONObject jo = new JSONObject();
		jo.put("type", queryType);
		jo.put("id", id);
		return jo.toJSONString();
	}
	
	public static QueryGetById fromJson(String json) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject) parser.parse(json);
		int id = (int) jo.get("id");
		QueryGetById qgbi = new QueryGetById(id);
		return qgbi;
	}
}
