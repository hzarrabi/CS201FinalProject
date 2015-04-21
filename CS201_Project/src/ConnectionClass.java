//import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ConnectionClass {
	
	static java.sql.Connection conn;
	
	public ConnectionClass()
	{
		connect();
	}
	
	private void connect(){
	 try {
		 Class.forName("com.mysql.jdbc.Driver");
		 conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
		 } catch (ClassNotFoundException e) {
		 e.printStackTrace();
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
}

	public static void main(String [] args)
	{
		new ConnectionClass();
		new FirstPageGUI();
//		try {
//			new MusicLibrary();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
