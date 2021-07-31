package proj2_Gili;
import java.sql.*;

// connection to web DB class
public class MyConnection {
	private MyConnection() {}
	static private Connection con;
	static public Connection getConnection() throws ClassNotFoundException, SQLException {
		if(con==null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3307/gili_db";
			con=DriverManager.getConnection(url,"root","");
			System.out.println("Xampp mysql connected..");
		}
		
		return con;
	}
}
