import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FeedGUI extends JPanel{
	private Dimension dim;
	private ArrayList<Activity> activities;
	public FeedGUI()
	{
		
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		activities = new ArrayList<Activity>();
		//setSize(dim);
		setSize(dim.width/3, dim.height);
		dim = new Dimension(dim.width/3, dim.height);
		this.setPreferredSize(dim);
		setBounds(0,0,dim.width/3, dim.height);
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setVisible(true);
		makeActivities();
		makeGUI();
		//setResizable(false);
	}
	
	public void makeGUI()
	{
		Collections.sort(activities);
		for (int i = activities.size()-1; i > -1; i--)
		{
			Activity act = activities.get(i);
			addActivity(act);
		}
		
	}
	
	public void addActivity(Activity act)
	{
		JPanel newActivity = new JPanel();
		JButton userButton = new JButton();
		JButton songButton = new JButton();
		//JLabel userName = new JLabel();
		JLabel timeStamp = new JLabel("");
		JLabel description = new JLabel("");
		description.setPreferredSize(new Dimension(dim.width/3, dim.height/10));
		MusicModel model = null;
		String username = "";
		
		//System.out.println(act.getSongID());
		try
		{
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT first_name, last_name FROM user_table WHERE iduser_table = " + act.getUserID();
			System.out.println(act.getUserID());
			ResultSet rs = st.executeQuery(queryCheck);
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next())
			{
				System.out.println("is this one working?");
				username = rs.getString(1) + " " + rs.getString(2);
			}
			st.close();
			
			Statement st1 = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck1 = "SELECT song_name FROM music_table WHERE idmusic_table = " + act.getSongID();
			ResultSet rs1 = st1.executeQuery(queryCheck1);
			int columns1 = rs1.getMetaData().getColumnCount();
			while (rs1.next())
			{
				System.out.println("here");
				model = LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(rs1.getString(1));
			}
			st1.close();
		}
		catch (Exception e){}
		
		if (act.getDescription().equals("rate"))
		{
			description.setText(username + " rated "+model.getSongName()+ " by "+model.getArtistName());
		}
		else if (act.getDescription().equals("listen"))
		{
			description.setText(username + " favorited "+model.getSongName()+ " by "+model.getArtistName());
		}
		else 
		{
			description.setText(username + " listened to "+model.getSongName()+ " by "+model.getArtistName());
		}
		
		timeStamp.setText(act.getDateTime().toString());
		
		try
		{
			URL imageurl = new URL(model.getAlbumPath());
			BufferedImage img = ImageIO.read(imageurl);
			ImageIcon icon = new ImageIcon(img);
			Image ResizedImage = icon.getImage().getScaledInstance(dim.width/3, dim.width/3, Image.SCALE_SMOOTH);
			songButton.setIcon(new ImageIcon(ResizedImage));
		} catch (IOException e1)
		{
			
		}
		ImageIcon newIcon2 = new ImageIcon("data/MomAndMoose.jpg");
		Image img2 = newIcon2.getImage().getScaledInstance(dim.width/10, dim.width/20, Image.SCALE_SMOOTH);
		userButton.setIcon(new ImageIcon(img2));
		
		JPanel song = new JPanel();
		song.setPreferredSize(new Dimension(dim.width/3, dim.height/3));
		song.add(songButton);
		song.add(description);
		JPanel user = new JPanel();
		user.setPreferredSize(new Dimension(dim.width/5, dim.height/4));
		user.add(userButton);
		user.add(timeStamp);
		newActivity.add(user);
		newActivity.add(song);
		add(newActivity);
		
	}
	public void makeActivities(){
		
		try
		{
			//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT user_being_followed FROM friend_relationship WHERE user = " + Integer.toString(LoggedInDriverGUI.userID);
			ResultSet rs = st.executeQuery(queryCheck);
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next())
			{
				System.out.println("getting activity of follwing");
				Statement st2 = ConnectionClass.conn.createStatement();
				System.out.println(Integer.toString(rs.getInt(1)));
				//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
				String queryCheck2 = "SELECT activity_id, user_id, song_id, description, time_stamp FROM activity_feed WHERE user_id = " + rs.getInt(1);
				ResultSet rs2 = st2.executeQuery(queryCheck2);
				while (rs2.next())
				{
					//System.out.println("getting activity of follwing in while");
					int activity_id = rs2.getInt(1);
					int user_id = rs2.getInt(2);
					int song_id = rs2.getInt(3);
					String description = rs2.getString(4);
					Date timeStamp = rs2.getDate(5);
					//System.out.println(activity_id);
					Activity newActivity = new Activity(activity_id, user_id, song_id, description, timeStamp);
					activities.add(newActivity);
				}
				st2.close();
			}
			st.close();
			
			Statement st3 = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck3 = "SELECT activity_id, user_id, song_id, description, time_stamp FROM activity_feed WHERE user_id = " + LoggedInDriverGUI.userID;
			ResultSet rs3 = st3.executeQuery(queryCheck3);
			//int columns3 = rs3.getMetaData().getColumnCount();
			while (rs3.next())
			{
				int activity_id = rs3.getInt(1);
				int user_id = rs3.getInt(2);
				int song_id = rs3.getInt(3);
				String description = rs3.getString(4);
				Date timeStamp = rs3.getDate(5);
				//System.out.println(activity_id);
				Activity newActivity = new Activity(activity_id, user_id, song_id, description, timeStamp);
				activities.add(newActivity);
			}
			st3.close();
			System.out.println("after user act");
			System.out.println(Integer.toString(activities.size()));
		}
		catch (Exception e) {}
	}

}