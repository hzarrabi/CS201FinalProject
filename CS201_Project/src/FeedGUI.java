import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.mysql.jdbc.PreparedStatement;


public class FeedGUI extends JPanel{
	private Dimension dim;
	private ArrayList<Activity> activities;
	private LoggedInDriverGUI mainPage;
	private ImageIcon fullHeart;
	private ImageIcon emptyHeart;
	private ImageIcon fullStar;
	private ImageIcon emptyStar;
	private ImageIcon clockIcon;
	private Dimension forProfile;
	
	public FeedGUI(LoggedInDriverGUI lidg, Dimension d, Dimension forProfile)
	{
		mainPage = lidg;
		dim = d;
		this.forProfile = forProfile;
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		activities = new ArrayList<Activity>();
		//setSize(dim);
		setSize(dim.width/3, dim.height*20);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	//	dim = new Dimension(dim.width/3, dim.height);
		//this.setPreferredSize(dim);
		setBounds(0,0,dim.width/3, dim.height);
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(FirstPageGUI.darkGrey);
		//setResizable(false);
	}
	
	public void make()
	{
		makeActivities();
		makeGUI();
		setVisible(true);
	}
	
	public void makeGUI()
	{
		emptyHeart = new ImageIcon("data/heartOutlineWhite.png");
		fullHeart = new ImageIcon("data/fullHeartWhite.png");
		emptyStar = new ImageIcon("data/starOutlineWhite.png");
		fullStar = new ImageIcon("data/star1.png");
		clockIcon = new ImageIcon("data/clock.png");
		Collections.sort(activities);
		//System.out.println("Size of activities array " + activities.size());
		for (int i = activities.size()-1; i > -1; i--)
		{
			Activity act = activities.get(i);
			addActivity(act);
		}
		
	}
	
	public void addActivity(Activity act)
	{
		JPanel newActivity = new JPanel();
		newActivity.setBackground(FirstPageGUI.darkGrey);
		newActivity.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel timeAndUser = new JPanel();
		timeAndUser.setBackground(FirstPageGUI.darkGrey);
		timeAndUser.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel songAndInfo = new JPanel();
		songAndInfo.setBackground(FirstPageGUI.darkGrey);
		songAndInfo.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel rateAndFavorite = new JPanel();
		rateAndFavorite.setBackground(FirstPageGUI.darkGrey);
		rateAndFavorite.setLayout(new FlowLayout(FlowLayout.CENTER));
		newActivity.setPreferredSize(new Dimension(dim.width, dim.width));
		timeAndUser.setPreferredSize(new Dimension(dim.width, dim.width/10));
		songAndInfo.setPreferredSize(new Dimension(dim.width, dim.width/10));
		rateAndFavorite.setPreferredSize(new Dimension(5*dim.width/6, dim.width/10));
		
		
		JButton userButton = new JButton("");
		JButton songButton = new JButton();
		JLabel timeStamp = new JLabel("");
		timeStamp.setPreferredSize(new Dimension(dim.width/4, dim.width/10));
		timeStamp.setFont(FirstPageGUI.smallFont);
		timeStamp.setForeground(FirstPageGUI.white);
		JLabel description = new JLabel("");
		description.setFont(FirstPageGUI.smallFont);
		description.setPreferredSize(new Dimension(dim.width, dim.width/10));
		description.setHorizontalAlignment(SwingConstants.CENTER);
		//description.setFont(FirstPageGUI.smallerFont);
		description.setForeground(FirstPageGUI.white);
		MusicModel model = null;
		String username = "";
		String profilePath = "";
		//System.out.println(act.getSongID());
		try
		{
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT first_name, last_name, profile_picture FROM user_table WHERE iduser_table = " + act.getUserID();
		//	System.out.println(act.getUserID());
			ResultSet rs = st.executeQuery(queryCheck);
			//int columns = rs.getMetaData().getColumnCount();
			while (rs.next())
			{
				//System.out.println("is this one working?");
				username = rs.getString(1) + " " + rs.getString(2);
				profilePath = rs.getString(3);
			}
			st.close();
			
			Statement st1 = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck1 = "SELECT song_name FROM music_table WHERE idmusic_table = " + act.getSongID();
			ResultSet rs1 = st1.executeQuery(queryCheck1);
		//	int columns1 = rs1.getMetaData().getColumnCount();
			while (rs1.next())
			{
				//System.out.println("here");
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
			Image ResizedImage = icon.getImage().getScaledInstance(dim.width/2, dim.width/2, Image.SCALE_SMOOTH);
			songButton.setIcon(new ImageIcon(ResizedImage));
			songButton.setOpaque(false);
			songButton.setContentAreaFilled(false);
			songButton.setBorderPainted(false);
		} catch (IOException e1)
		{
			
		}
		
		if (profilePath == null)
		{
			//System.out.println("here");
			ImageIcon icon = new ImageIcon("data/headphone_default.jpg");
			Image ResizedImage = icon.getImage().getScaledInstance(dim.width/15, dim.width/15, Image.SCALE_SMOOTH);
			userButton.setIcon(new ImageIcon(ResizedImage));
			userButton.setOpaque(false);
			userButton.setContentAreaFilled(false);
			userButton.setBorderPainted(false);
		}
		else
		{
			try
			{
				URL imageurl = new URL(profilePath);
				BufferedImage img = ImageIO.read(imageurl);
				ImageIcon icon = new ImageIcon(img);
				Image ResizedImage = icon.getImage().getScaledInstance(dim.width/15, dim.width/15, Image.SCALE_SMOOTH);
				userButton.setIcon(new ImageIcon(ResizedImage));
				userButton.setOpaque(false);
				userButton.setContentAreaFilled(false);
				userButton.setBorderPainted(false);
			} catch (IOException e1)
			{
				
			}
		}
		userButton.addActionListener(new ActionListenerProfile(act.getUserID(), "friends"));
		//timeAndUser = new JPanel();
		//songAndInfo = new JPanel();
		//rateAndFavorite = new JPanel();
		JLabel clock = new JLabel("");
		clock.setPreferredSize(new Dimension(dim.width/20, dim.width/20));
		clock.setIcon(clockIcon);
		newActivity.add(timeAndUser);
		newActivity.add(songButton);
		newActivity.add(songAndInfo);
		newActivity.add(rateAndFavorite);
		timeAndUser.add(userButton);
		timeAndUser.add(clock);
		timeAndUser.add(timeStamp);
		songAndInfo.add(description);
		
		JButton oneStar = new JButton();
		JButton twoStar = new JButton();
		JButton threeStar = new JButton();
		JButton fourStar = new JButton();
		JButton fiveStar = new JButton();
		JButton favoriteButton = new JButton();
		favoriteButton.setOpaque(false);
		favoriteButton.setContentAreaFilled(false);
		favoriteButton.setBorderPainted(false);
		favoriteButton.addActionListener(new FavoriteActionListener(model, favoriteButton));
		favoriteButton.setPreferredSize(new Dimension(dim.width/6, dim.height/13));
		
		try
		{
			//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + LoggedInDriverGUI.userID + " AND song_id = " + act.getSongID();
			ResultSet rs = st.executeQuery(queryCheck);
			//int columns = rs.getMetaData().getColumnCount();
			if (!rs.next())
			{
				//System.out.println("THIS NOT WAS NOT FAVORITES "+act.getSongID());
				favoriteButton.setIcon(emptyHeart);
			}
			else
			{
				//System.out.println("THIS SONG IS ALREADY FAVORITED " + rs.getInt(1) + " ACT ID "+act.getSongID());
				favoriteButton.setIcon(fullHeart);
			}
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		
		oneStar.setOpaque(false);
		oneStar.setContentAreaFilled(false);
		oneStar.setBorderPainted(false);
		oneStar.setIcon(emptyStar);
		oneStar.setPreferredSize(new Dimension(dim.width/6, dim.height/13));
		
		fiveStar.setOpaque(false);
		fiveStar.setContentAreaFilled(false);
		fiveStar.setBorderPainted(false);
		fiveStar.setIcon(emptyStar);
		fiveStar.setPreferredSize(new Dimension(dim.width/6, dim.height/13));
		
		twoStar.setOpaque(false);
		twoStar.setContentAreaFilled(false);
		twoStar.setBorderPainted(false);
		twoStar.setIcon(emptyStar);
		twoStar.setPreferredSize(new Dimension(dim.width/6, dim.height/13));
		
		threeStar.setOpaque(false);
		threeStar.setContentAreaFilled(false);
		threeStar.setBorderPainted(false);
		threeStar.setIcon(emptyStar);
		threeStar.setPreferredSize(new Dimension(dim.width/6, dim.height/13));
		
		fourStar.setOpaque(false);
		fourStar.setContentAreaFilled(false);
		fourStar.setBorderPainted(false);
		fourStar.setIcon(emptyStar);
		fourStar.setPreferredSize(new Dimension(dim.width/6, dim.height/13));
		JLabel empty = new JLabel();
		JLabel empty2 = new JLabel();
		empty.setPreferredSize(new Dimension(dim.width/10, dim.height/13));
		empty2.setPreferredSize(new Dimension(dim.width/10, dim.height/13));
		rateAndFavorite.setLayout(new BoxLayout(rateAndFavorite, BoxLayout.X_AXIS));
		//Box.crea
		rateAndFavorite.add(empty);
		rateAndFavorite.add(oneStar);
		rateAndFavorite.add(twoStar);
		rateAndFavorite.add(threeStar);
		rateAndFavorite.add(fourStar);
		rateAndFavorite.add(fiveStar);
		rateAndFavorite.add(empty2);
		//Box.createGlue();
		rateAndFavorite.add(favoriteButton);
		songButton.addActionListener(new ActionListenerSong(model, forProfile));
		oneStar.addActionListener(new StarActionListener(oneStar, twoStar, threeStar, fourStar, fiveStar, 1, model));
		twoStar.addActionListener(new StarActionListener(oneStar, twoStar, threeStar, fourStar, fiveStar, 2, model));
		threeStar.addActionListener(new StarActionListener(oneStar, twoStar, threeStar, fourStar, fiveStar, 3, model));
		fourStar.addActionListener(new StarActionListener(oneStar, twoStar, threeStar, fourStar, fiveStar, 4, model));
		fiveStar.addActionListener(new StarActionListener(oneStar, twoStar, threeStar, fourStar, fiveStar, 5, model));
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
			int j = 0;
			while (rs.next() && j < 11)
			{
				j++;
				//System.out.println("getting activity of follwing");
				Statement st2 = ConnectionClass.conn.createStatement();
				//System.out.println(Integer.toString(rs.getInt(1)));
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
			int i = 0;
			while (rs3.next() && i<9)
			{
				i++;
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
			//System.out.println("after user act");
			//System.out.println(Integer.toString(activities.size()));
		}
		catch (Exception e) {}
	}
	
	class ActionListenerProfile implements ActionListener{
		private int id;
		private String relation;
		public ActionListenerProfile(int ID, String relationship)
		{
			relation = relationship;
			id = ID;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println("in actionlistener");
			//String sqlQuery = "SELECT COUNT(1) FROM friend_relationship WHERE EXISTS user = "+ id+" AND user_being_followed = "+id+")";
			
			ProfileGUI newProfile = new ProfileGUI(mainPage,forProfile, relation, id);
			mainPage.addCurrent(newProfile);
		}
		
	}


class StarActionListener implements ActionListener{
	private JButton one;
	private JButton two;
	private JButton three;
	private JButton four;
	private JButton five;
	private int key;
	private MusicModel musicObject;
	
	 StarActionListener(JButton oneStar, JButton twoStar, JButton threeStar, JButton fourStar, JButton fiveStar, int key, MusicModel musicObject)
	{
		one = oneStar;
		two = twoStar;
		three = threeStar;
		four = fourStar;
		five = fiveStar;
		this.key = key;
		this.musicObject = musicObject;
	}
	public void actionPerformed(ActionEvent e) {
		int myRating = 0;
		if (key == 4)
		{
			one.setIcon(fullStar);
			two.setIcon(fullStar);
			three.setIcon(fullStar);
			four.setIcon(fullStar);
			five.setIcon(emptyStar);
			myRating = 4;
		}
		else if (key == 1)
		{
			one.setIcon(fullStar);
			two.setIcon(emptyStar);
			three.setIcon(emptyStar);
			four.setIcon(emptyStar);
			five.setIcon(emptyStar);
			myRating = 1;
		}
		else if (key == 2)
		{
			one.setIcon(fullStar);
			two.setIcon(fullStar);
			three.setIcon(emptyStar);
			four.setIcon(emptyStar);
			five.setIcon(emptyStar);
			myRating = 2;
		}
		else if (key == 3)
		{
			one.setIcon(fullStar);
			two.setIcon(fullStar);
			three.setIcon(fullStar);
			four.setIcon(emptyStar);
			five.setIcon(emptyStar);
			myRating = 3;
		}
		else if (key == 5)
		{
			one.setIcon(fullStar);
			two.setIcon(fullStar);
			three.setIcon(fullStar);
			four.setIcon(fullStar);
			five.setIcon(fullStar);
			myRating = 5;
		}
		
		try
		{
			PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
			ps.setInt(1, LoggedInDriverGUI.userID);
			ps.setString(2, "rate "+myRating);
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		    ps.setInt(3, musicObject.getMusicID());
		    ps.setTimestamp(4, sqlDate);
			ps.executeUpdate();
			ps.close();
			//beingPlayed = true;
		} 
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}

}

	class FavoriteActionListener implements ActionListener{

		JButton favoriteLabel;
		MusicModel model;
		public FavoriteActionListener(MusicModel musicObject, JButton favoriteButton)
		{
			favoriteLabel = favoriteButton;
			model = musicObject;
					
		}
		public void actionPerformed(ActionEvent e) {
			try
			{
			//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID) +" AND song_id = " + Integer.toString(model.getMusicID());
			ResultSet rs = st.executeQuery(queryCheck);
			int columns = rs.getMetaData().getColumnCount();
			
			if (favoriteLabel.getIcon() == emptyHeart)
			{
				//System.out.println("should be here");
				favoriteLabel.setIcon(fullHeart);
		//		if (!musicObject.getFavoritedBool())
		//		{

						if (!rs.next()) 
						{
							try
							{
								//inserting into favorited_songs table
								PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO favorite_songs (user_id, song_id)" + "VALUES (?, ?)");
								ps.setInt(1, LoggedInDriverGUI.userID);
								ps.setInt(2, model.getMusicID());
								ps.executeUpdate();
								ps.close();
								//inserting into activity_feed table
								PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
								ps1.setInt(1, LoggedInDriverGUI.userID);
								ps1.setString(2, "favorite");
								java.util.Date utilDate = new java.util.Date();
							    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
							    ps1.setInt(3, model.getMusicID());
							    ps1.setTimestamp(4, sqlDate);
								ps1.executeUpdate();
								ps1.close();
							} 
							catch (SQLException e1)
							{
								e1.printStackTrace();
							}
						}			
			//		musicObject.setFavoritedBool(true);
			//	}
			}
			else
			{
				favoriteLabel.setIcon(emptyHeart);
						if (rs.next()) 
						{
							try
							{
								PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("DELETE FROM favorite_songs WHERE " + "user_id = ?" + " and " + "song_id = ?");
								ps.setInt(1, LoggedInDriverGUI.userID);
								ps.setInt(2, model.getMusicID());
								ps.executeUpdate();
								ps.close();
							} 
							catch (SQLException e1)
							{
								e1.printStackTrace();
							}
							
						}
			}
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			
		}
		
	}
	
	class ActionListenerSong implements ActionListener{
		private MusicModel model;
		private Dimension dim;
		public ActionListenerSong(MusicModel model, Dimension dim)
		{
			this.model = model;
			this.dim = dim;
		}
		public void actionPerformed(ActionEvent e) {
				//IndpMusicPlayer player = new IndpMusicPlayer
			IndpMusicPlayer player = new IndpMusicPlayer(model, dim);
				mainPage.addCurrent(player);
			
		}
		
	}
	
	public void refresh()
	{
		
	}
}

