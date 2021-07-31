package proj2_Gili;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import org.json.simple.JSONObject;

public class ListOfItem {

	// List
	List<Item> list = new ArrayList<Item>();
	
	// Json list
	JSONObject jObjItems = new JSONObject();
	

	// saving
	public void save(String fileName) throws FileNotFoundException, IOException {
		try(ObjectOutputStream out= new ObjectOutputStream(
				new FileOutputStream(fileName))){
		out.writeObject(this);
		}
	}
	
	// list creation
	public static ListOfItem create(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ListOfItem loi=null;
		try(ObjectInputStream in= new ObjectInputStream(
				new FileInputStream(fileName))){
			loi=(ListOfItem)in.readObject();
		} 
		return loi;
	}
	
	
	// add item
	@SuppressWarnings("unused")
	public void add(Item item) throws IOException {
		
		// adds item to the list
		list.add(item);
		
		// adds item to json
		Writer output = null;
	}
	
	// create & add item
	public void insert(String name, double price, int amount) throws IOException, ClassNotFoundException, SQLException {
		Item newItem = new Item(name,price, amount);
		this.add(newItem);
	}
	
	public JSONObject getListItemsJson() {
		return jObjItems;
	}

	// Search item by prefix name starts with
	@SuppressWarnings("unchecked")
	public JSONObject findByPrefix(String prefix)throws IOException{
		List<Item> res = new ArrayList<Item>();
		JSONObject found = new JSONObject();
		JSONObject nothingFound = new JSONObject();
		nothingFound.put("result", "no_item_start_with_"+prefix);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().startsWith(prefix)) {
				res.add(list.get(i));
				found.put("found", prefix);
				found.put("object", list.get(i).getItemJsonObj());
			}
			else {
				;
			}
		}
		if(found.isEmpty()) {
			return nothingFound;
		}
		else {
			return found;
		}
	}
	
	// Search item by ID
	@SuppressWarnings("unchecked")
	public JSONObject getById(int id) throws IOException{
		JSONObject nothingFound = new JSONObject();
		nothingFound.put("result", "no_item_with_id_"+id);
		JSONObject found = new JSONObject();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID()==id) {
				found.put("found", id);
				found.put("item_name", list.get(i).getName());
			}
			else {
				;
			}
		}
		if(found.isEmpty()) {
			return nothingFound;
		}
		else {
			return found;
		}
	}
}