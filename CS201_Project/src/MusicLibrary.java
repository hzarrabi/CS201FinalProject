
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

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

	private HashMap<String, MusicModel> musicModelMap;
	private ArrayList <MusicModel> topListenedSongs;
	private ArrayList<MusicModel> topRatedSongs;
	
	public HashMap<String,MusicModel> getMusicModelMap(){
		return musicModelMap;
	}
	
	public ArrayList<MusicModel> getTopListenedSongs(){
		return topListenedSongs;
	}
	
	public ArrayList<MusicModel> getTopRatedSongs(){
		return topRatedSongs;
	}
	
	public MusicLibrary() throws Exception{
		super("MUSIC");
		setSize(500,600);
		setLocation(500,0);
		//connect();
		
		//musicModelVector = new Vector<MusicModel> ();
		musicModelMap = new HashMap<String, MusicModel> ();
		
		try{
			
			Statement st = ConnectionClass.conn.createStatement();
			String queryCheck = "SELECT * from music_table"; //WHERE song_name";
			PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement(queryCheck);
			ResultSet rs = ps.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			while (rs.next()){

				//this is where we are creating our Music Objects
				MusicModel MusicObject = new MusicModel();
				
				//adding all of the values from the database to the object
				MusicObject.setMusicID(rs.getInt(1));
				MusicObject.setSongName(rs.getString(2));
				MusicObject.setArtistName(rs.getString(3));
				MusicObject.setRatingSum(rs.getInt(4));
				MusicObject.setNumberOfRatings(rs.getInt(5));
				MusicObject.setnumberOfPlayCounts(rs.getInt(6));
				MusicObject.setSongPath(rs.getString(7));
				MusicObject.setAlbumPath(rs.getString(8));
				MusicObject.setPlayButtonThatLeadsToMusicPlayer(rs.getString(2));
				
				musicModelMap.put(MusicObject.getSongName(),MusicObject);
				
				System.out.println("");
			}

		}catch(Exception E){
			E.printStackTrace();	
		}
		topListenedSongs = new ArrayList<MusicModel>();
		try{
			
			Statement st = ConnectionClass.conn.createStatement();
			String queryCheck = "SELECT * from music_table ORDER BY numb_playe_count DESC"; //WHERE song_name";
			PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement(queryCheck);
			ResultSet rs = ps.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			while (rs.next()){

				//this is where we are creating our Music Objects
				MusicModel MusicObject = new MusicModel();
				
				//adding all of the values from the database to the object
				MusicObject.setMusicID(rs.getInt(1));
				MusicObject.setSongName(rs.getString(2));
				MusicObject.setArtistName(rs.getString(3));
				MusicObject.setRatingSum(rs.getInt(4));
				MusicObject.setNumberOfRatings(rs.getInt(5));
				MusicObject.setnumberOfPlayCounts(rs.getInt(6));
				MusicObject.setSongPath(rs.getString(7));
				MusicObject.setAlbumPath(rs.getString(8));
				MusicObject.setPlayButtonThatLeadsToMusicPlayer(rs.getString(2));
				topListenedSongs.add(MusicObject);
			} 
		}catch (Exception e)
		{
				
		}
		topRatedSongs = new ArrayList<MusicModel>();
		try{
			
			Statement st = ConnectionClass.conn.createStatement();
			String queryCheck = "SELECT * from music_table ORDER BY CAST(rating_sum AS DECIMAL)/numb_of_ratings DESC"; //WHERE song_name";
			PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement(queryCheck);
			ResultSet rs = ps.executeQuery();
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			while (rs.next()){

				//this is where we are creating our Music Objects
				MusicModel MusicObject = new MusicModel();
				
				//adding all of the values from the database to the object
				MusicObject.setMusicID(rs.getInt(1));
				MusicObject.setSongName(rs.getString(2));
				MusicObject.setArtistName(rs.getString(3));
				MusicObject.setRatingSum(rs.getInt(4));
				MusicObject.setNumberOfRatings(rs.getInt(5));
				MusicObject.setnumberOfPlayCounts(rs.getInt(6));
				MusicObject.setSongPath(rs.getString(7));
				MusicObject.setAlbumPath(rs.getString(8));
				MusicObject.setPlayButtonThatLeadsToMusicPlayer(rs.getString(2));
				topRatedSongs.add(MusicObject);
			} 
		}catch (Exception e)
		{
				
		}
	}
}
