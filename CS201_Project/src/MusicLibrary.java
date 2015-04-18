
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

public class MusicLibrary extends JFrame {
	public static final long serialVersionUID = 1;
	
	
	//fix this cause its bad coding style
	Connection connection;
	String dburl;
	String userName;
	String passWord;
	Connection conn;

	private void connect(){
		connection = null;
		dburl = "jdbc:mysql://104.236.176.180:3306/cs201";
		userName = "cs201";
		passWord = "manishhostage";

			 try {
				 Class.forName("com.mysql.jdbc.Driver");
				 conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
				 } catch (ClassNotFoundException e) {
				 e.printStackTrace();
				 } catch (SQLException e) {
				 e.printStackTrace();
				 }
	}

	
	
	public MusicLibrary() throws Exception{
		super("MUSIC");
		setSize(500,600);
		setLocation(500,0);
		connect();
		
		try{
			Statement st = conn.createStatement();
			String queryCheck = "SELECT * from music_table"; //WHERE song_name";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(queryCheck);
			ResultSet rs = ps.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()){
				for (int i = 1; i <= columnsNumber; i++){
					if (i > 1){
						System.out.print(", ");
					}
					String columnValue = rs.getString(i);
					System.out.println(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
			//System.out.println(rs.absolute(1));

			//System.out.println(ps.toString());
			//ALO

		}catch(Exception E){
			E.printStackTrace();
			
		}
	


	}
}
