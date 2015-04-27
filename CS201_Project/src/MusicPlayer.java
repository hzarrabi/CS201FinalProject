import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.PreparedStatement;


public class MusicPlayer extends JPanel{
	private Dimension dim;
	private int song;
	private JLabel album;
	private ImageIcon albumImage;
	private JLabel artist;
	private JLabel rating;
	private JButton backButton;
	private JButton playButton;
	private JButton forwardButton;
	private JButton pauseButton;
	private JTextArea lyrics;
	private String songName;
	private MusicModel musicObject;
	private static Thread myThread;
	private JButton rateButton;
	private JButton commentButton;
	private JButton favoriteButton;
	
	private JPanel tabPanelMain;
	private ArrayList<JButton> allButtons;
	private ArrayList<MusicModel> allSongs;
	//keeps track of the current song in the allSongs arraylist, NOT the song id in SQL
	private int currentSong;

	private JPanel favoritePanel;
	private JPanel commentPanel;
	private JPanel comments;
	private JTextField comment;
	private JButton enter;
	private JScrollPane jspComments;
	private JPanel ratePanel;
	
	private int myRating;
	JButton oneStar;
	JButton twoStar;
	JButton threeStar;
	JButton fourStar;
	JButton fiveStar;
	
	ArrayList<JButton> ratingButtons;
	private ImageIcon emptyStar;
	private ImageIcon fullStar;
	private int currentPanel;
	private JButton favoriteLabel;
	private Icon emptyHeart;
	private ImageIcon fullHeart;
	private JLabel listens;
	private JPanel ratingPanel;
	JPanel mainPanel;
	
	private boolean beingPlayed = false;
	
	public MusicPlayer(Dimension d, ArrayList<JButton> buttons, ArrayList<MusicModel> songs, int currentSong)
	{
		this.currentSong = currentSong;
		this.allButtons = buttons;
		dim = d;
		this.allSongs = songs;
		emptyStar = new ImageIcon("data/starOutline.png");
		fullStar = new ImageIcon("data/starFullBlack.png");
		musicObject = allSongs.get(currentSong);
		songName = musicObject.getSongName();
		initializeComponents();
		createUserGUI();
		setEventHandlers();
		setVisible(true);
		repaint();

	}
	
	public MusicPlayer(Dimension d, ArrayList<JButton> buttons, Boolean b, ArrayList<MusicModel> songs, int currentSong)
	{
		this.currentSong = currentSong;
		this.allButtons = buttons;
		dim = d;
		this.allSongs = songs;
		emptyStar = new ImageIcon("data/starOutline.png");
		fullStar = new ImageIcon("data/starFullBlack.png");
		musicObject = allSongs.get(currentSong);
		songName = musicObject.getSongName();
		initializeComponents();
		createUserGUI();
		setEventHandlers();
		setVisible(true);
		repaint();

	}
	
	public static void stopThread()
	{
		if (myThread != null)
			myThread.suspend();
	}	
	
	private void initializeComponents(){
		this.setSize(dim);
		currentPanel = 0;
		album = new JLabel("");
		album.setPreferredSize(new Dimension(3*dim.width/4, 3*dim.width/4));
		favoritePanel = new JPanel();
		commentPanel = new JPanel();
		
		ratePanel = new JPanel();
		comments = new JPanel();
		comments.setBackground(Color.BLACK);
		comment = new JTextField("comment");
		enter = new JButton("Enter");
		jspComments = new JScrollPane(comments);
		ratingButtons = new ArrayList<JButton>();
		setPreferredSize(new Dimension(dim.width, dim.height));
		
		try {
            URL imageurl = new URL(musicObject.getAlbumPath());
            BufferedImage img = ImageIO.read(imageurl);
            ImageIcon icon = new ImageIcon(img);
            Image ResizedImage = icon.getImage().getScaledInstance(3*dim.width/4, 3*dim.width/4, Image.SCALE_SMOOTH);
            album.setIcon(new ImageIcon(ResizedImage));
         } catch (IOException e) {
            e.printStackTrace();
         }
		
		
		artist = new JLabel(musicObject.getSongName() + " "+musicObject.getArtistName());
		artist.setPreferredSize(new Dimension(dim.width-10, dim.height/26));
		ratingPanel = new JPanel();
		ratingPanel.setPreferredSize(new Dimension(dim.width-10, dim.height/20));
		ratingPanel.setBackground(FirstPageGUI.white);
		rating = new JLabel("Overall Rating: ");
		ratingPanel.add(rating);
		listens = new JLabel("# of listens");
		listens.setPreferredSize(new Dimension(dim.width-10, dim.height/26));
		listens.setFont(FirstPageGUI.smallFont);
		listens.setForeground(FirstPageGUI.darkGrey);
		listens.setHorizontalAlignment(SwingConstants.CENTER);

		backButton = new JButton();
		
		playButton = new JButton();
		forwardButton = new JButton();
		pauseButton = new JButton();

		rateButton = new JButton();
		commentButton = new JButton();
		favoriteButton = new JButton();
	}
	
	private void createUserGUI()
	{
		setBackground(FirstPageGUI.white);
		JPanel buttonPanel = new JPanel();
		//buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width/50, dim.height));
		buttonPanel.setPreferredSize(new Dimension(dim.width, 6*dim.height/93));
		buttonPanel.setBackground(FirstPageGUI.green);
		
		commentButton.setOpaque(false);
		commentButton.setContentAreaFilled(false);
		commentButton.setBorderPainted(false);
		commentButton.setIcon(new ImageIcon("data/commentsSmall.png"));
		commentButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		rateButton.setOpaque(false);
		rateButton.setContentAreaFilled(false);
		rateButton.setBorderPainted(false);
		rateButton.setIcon(new ImageIcon("data/ratingSmall.png"));
		rateButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		favoriteButton.setOpaque(false);
		favoriteButton.setContentAreaFilled(false);
		favoriteButton.setBorderPainted(false);
		favoriteButton.setIcon(new ImageIcon("data/favorite_emptySmall.png"));
		favoriteButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		buttonPanel.add(commentButton);
		buttonPanel.add(favoriteButton);
		buttonPanel.add(rateButton);
		
		JPanel bottomPanel = new JPanel();
		//bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width/50, dim.height));
		bottomPanel.setPreferredSize(new Dimension(dim.width, 6*dim.height/93));
		bottomPanel.setBackground(FirstPageGUI.green);
	
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setIcon(new ImageIcon("data/ReverseButtonSmall.png"));
		backButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		playButton.setIcon(new ImageIcon("data/playButtonSmall.png"));
		playButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		pauseButton.setOpaque(false);
		pauseButton.setContentAreaFilled(false);
		pauseButton.setBorderPainted(false);
		pauseButton.setIcon(new ImageIcon("data/pauseButtonSmall.png"));
		pauseButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		forwardButton.setOpaque(false);
		forwardButton.setContentAreaFilled(false);
		forwardButton.setBorderPainted(false);
		forwardButton.setIcon(new ImageIcon("data/forwardButtonSmall.png"));
		forwardButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		bottomPanel.add(backButton);
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		bottomPanel.add(forwardButton);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(FirstPageGUI.white);
		mainPanel.setPreferredSize(new Dimension(dim.width, 50*dim.height/93));
		mainPanel.add(album);
		mainPanel.add(artist);
		artist.setBackground(FirstPageGUI.white);
		rating.setBackground(FirstPageGUI.white);
		artist.setForeground(FirstPageGUI.darkGrey);
		rating.setForeground(FirstPageGUI.darkGrey);
		artist.setFont(FirstPageGUI.smallFont);
		rating.setFont(FirstPageGUI.smallFont);
		rating.setHorizontalAlignment(SwingConstants.CENTER);
		artist.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(ratingPanel);
		mainPanel.add(listens);
		mainPanel.add(bottomPanel);
		
		favoriteLabel = new JButton();
		favoritePanel = new JPanel();
		favoritePanel.setPreferredSize(new Dimension(dim.width, 15*dim.height/93));
		favoriteLabel.setPreferredSize(new Dimension(dim.width, 15*dim.height/93));
		favoritePanel.add(favoriteLabel);
		
		emptyHeart = new ImageIcon("data/heartOutline.png");
		fullHeart = new ImageIcon("data/fullHeartBlack.png");
		favoriteLabel.setOpaque(false);
		favoriteLabel.setContentAreaFilled(false);
		favoriteLabel.setBorderPainted(false);
		//favoriteLabel.setIcon(emptyHeart);
		
		//fiveStar.setPreferredSize(new Dimension(dim.width/6, dim.height/13));
		commentPanel = new JPanel();
		commentPanel.setPreferredSize(new Dimension(dim.width, 21*dim.height/93));
		ratePanel.setPreferredSize(new Dimension(dim.width,21*dim.height/93));
		comments.setPreferredSize(new Dimension(dim.width, 15*dim.height/93));
		comment.setPreferredSize(new Dimension(3*dim.width/5, 3*dim.height/93));
		jspComments.setPreferredSize(new Dimension(dim.width, 15*dim.height/93));
		//comments.setBackground(FirstPageGUI.darkGrey);
		//jspComments.setBackground(FirstPageGUI.darkGrey);
		//commentPanel.setBackground(FirstPageGUI.darkGrey);
		favoritePanel.setBackground(FirstPageGUI.white);
		commentPanel.setBackground(FirstPageGUI.white);
		comments.setBackground(FirstPageGUI.white);
		String query = "SELECT * from comments_table";
		try {
			Statement st = ConnectionClass.conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			int columns = rs.getMetaData().getColumnCount();
			Vector<Integer> userIDVector = new Vector<Integer> ();
			Vector<String> commentVector = new Vector<String> ();
			while (rs.next())
			{
				int ID = 0;
				for (int i = 1; i <= columns; i++)
				{
					if (i == 1)
					{
						userIDVector.add(rs.getInt(i));
						//System.out.println("ID: " + rs.getInt(i));
						ID = rs.getInt(i);
					}
					if (i == 2)
					{
						commentVector.add(rs.getString(i));
						//System.out.println("Comment: " + rs.getString(i));
					}
				}
				String query1 = "Select username from user_table where iduser_table = " + Integer.toString(ID);
				Statement st1 = ConnectionClass.conn.createStatement();
				ResultSet rs1 = st1.executeQuery(query1);
				int columns1 = rs1.getMetaData().getColumnCount();
				while (rs1.next())
				{
					for (int i = 1;i <=columns1; i++)
					{
						System.out.println("Username: " + rs1.getString(i));
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
		
		
		
		
		ratePanel.setBackground(FirstPageGUI.white);
		enter.setPreferredSize(new Dimension(dim.width/5, 3*dim.height/93));
		
		enter.setBorder(new RoundedBorder());
		enter.setBackground(FirstPageGUI.darkGrey);
		enter.setForeground(FirstPageGUI.white);
		enter.setFont(FirstPageGUI.smallFont);
		enter.setOpaque(true);
		comment.setBorder(new RoundedBorder());
		comment.setBackground(FirstPageGUI.white);
		comment.setForeground(FirstPageGUI.darkGrey);
		comment.setFont(FirstPageGUI.smallFont);
		oneStar = new JButton();
		twoStar = new JButton();
		threeStar = new JButton();
		fourStar = new JButton();
		fiveStar = new JButton();
		
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
		
		ratingButtons.add(oneStar);
		ratingButtons.add(twoStar);
		ratingButtons.add(threeStar);
		ratingButtons.add(fourStar);
		ratingButtons.add(fiveStar);
		
		for (int i = 0; i <5; i++)
		{
			ratePanel.add(ratingButtons.get(i));
		}
		
		JPanel other = new JPanel();
		other.setPreferredSize(new Dimension(dim.width, 9*dim.height/93));
		other.add(enter, BorderLayout.WEST);
		other.add(comment, BorderLayout.EAST);
		commentPanel.add(jspComments, BorderLayout.CENTER);
		commentPanel.add(other, BorderLayout.SOUTH);

		JPanel tabPanel = new JPanel();
		
		
		tabPanel.setPreferredSize(new Dimension(dim.width, 28*dim.height/93));
		tabPanel.setBackground(FirstPageGUI.white);
		tabPanelMain = new JPanel();
		tabPanelMain.setPreferredSize(new Dimension(dim.width, 21*dim.height/93));
		//tabPanelMain.setBackground(Fir);
		//tabPanel.setBackground(FirstPageGUI.darkGrey);
		//tabPanelMain.setBackground(FirstPageGUI.darkGrey);
		tabPanelMain.add(commentPanel, BorderLayout.CENTER);
		tabPanelMain.setBackground(FirstPageGUI.white);
		tabPanel.add(tabPanelMain);
		tabPanel.add(buttonPanel);
		resetStuff();
		//setBackground(FirstPageGUI.green);
		//mainPanel.setBackground(FirstPageGUI.darkGrey);
		add(mainPanel, BorderLayout.NORTH);
		add(tabPanel, BorderLayout.SOUTH);
	}
	private void setEventHandlers(){
		favoriteLabel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{

				try
				{
				//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
				
				Statement st = ConnectionClass.conn.createStatement();
				//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
				String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID) +" AND song_id = " + Integer.toString(musicObject.getMusicID());
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
									ps.setInt(2, musicObject.getMusicID());
									ps.executeUpdate();
									ps.close();
									//inserting into activity_feed table
									PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
									ps1.setInt(1, LoggedInDriverGUI.userID);
									ps1.setString(2, "favorite");
									java.util.Date utilDate = new java.util.Date();
								    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
								    ps1.setInt(3, musicObject.getMusicID());
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
					//System.out.println("should not be here");
					favoriteLabel.setIcon(emptyHeart);
			//		if (musicObject.getFavoritedBool())
			//		{
							if (rs.next()) 
							{
								try
								{
									PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("DELETE FROM favorite_songs WHERE " + "user_id = ?" + " and " + "song_id = ?");
									ps.setInt(1, LoggedInDriverGUI.userID);
									ps.setInt(2, musicObject.getMusicID());
									ps.executeUpdate();
									ps.close();
								} 
								catch (SQLException e1)
								{
									e1.printStackTrace();
								}
								
							}
				//		musicObject.setFavoritedBool(false);
				//	}
				}
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		favoriteButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentPanel = 2;
				tabPanelMain.add(favoritePanel);
				//mainPanel.add(musicPlayerTopListened);
				tabPanelMain.revalidate();
	            tabPanelMain.repaint();
	            try
				{
					//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
					
					Statement st = ConnectionClass.conn.createStatement();
					//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
					String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID) + " AND song_id = " + Integer.toString(musicObject.getMusicID());
					ResultSet rs = st.executeQuery(queryCheck);
					int columns = rs.getMetaData().getColumnCount();
					if (rs.next())
					{
						favoriteLabel.setIcon(fullHeart);
					}
					else
					{
						favoriteLabel.setIcon(emptyHeart);
					}
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		
		rateButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentPanel = 1;
				tabPanelMain.add(ratePanel, BorderLayout.SOUTH);
				//mainPanel.add(musicPlayerTopListened);
				tabPanelMain.revalidate();
	            tabPanelMain.repaint();
				
			}
		});
		
		commentButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentPanel = 0;
				tabPanelMain.add(commentPanel);
				//mainPanel.add(musicPlayerTopListened);
				tabPanelMain.revalidate();
	            tabPanelMain.repaint();
				
			}
		});
		
		oneStar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				oneStar.setIcon(fullStar);
				twoStar.setIcon(emptyStar);
				threeStar.setIcon(emptyStar);
				fourStar.setIcon(emptyStar);
				fiveStar.setIcon(emptyStar);
				myRating = 1;
				
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setString(2, "rate 1");
					java.util.Date utilDate = new java.util.Date();
				    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
				    ps.setInt(3, musicObject.getMusicID());
				    ps.setTimestamp(4, sqlDate);
					ps.executeUpdate();
					ps.close();
					beingPlayed = true;
					//update ratings
					PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_of_ratings= ?, rating_sum= ? " + "WHERE idmusic_table = ?");
					ps1.setInt(1, musicObject.getNumberOfRatings()+1);
					ps1.setInt(2, musicObject.getRatingSum()+1);
					ps1.setInt(3, musicObject.getMusicID());
					ps1.executeUpdate();
					ps1.close();
					musicObject.setNumberOfRatings(musicObject.getNumberOfRatings()+1);
					musicObject.setRatingSum(musicObject.getRatingSum()+1);
					double rate = musicObject.getRatingSum()/musicObject.getNumberOfRatings();
					setRating(rate);
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		twoStar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				oneStar.setIcon(fullStar);
				twoStar.setIcon(fullStar);
				threeStar.setIcon(emptyStar);
				fourStar.setIcon(emptyStar);
				fiveStar.setIcon(emptyStar);
				myRating = 2;
				
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setString(2, "rate 2");
					java.util.Date utilDate = new java.util.Date();
				    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
				    ps.setInt(3, musicObject.getMusicID());
				    ps.setTimestamp(4, sqlDate);
					ps.executeUpdate();
					ps.close();
					beingPlayed = true;
					//update ratings
					PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_of_ratings= ?, rating_sum= ? " + "WHERE idmusic_table = ?");
					ps1.setInt(1, musicObject.getNumberOfRatings()+1);
					ps1.setInt(2, musicObject.getRatingSum()+2);
					ps1.setInt(3, musicObject.getMusicID());
					ps1.executeUpdate();
					ps1.close();
					musicObject.setNumberOfRatings(musicObject.getNumberOfRatings()+1);
					musicObject.setRatingSum(musicObject.getRatingSum()+2);
					double rate = musicObject.getRatingSum()/musicObject.getNumberOfRatings();
					setRating(rate);
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		threeStar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				oneStar.setIcon(fullStar);
				twoStar.setIcon(fullStar);
				threeStar.setIcon(fullStar);
				fourStar.setIcon(emptyStar);
				fiveStar.setIcon(emptyStar);
				myRating = 3;
				
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setString(2, "rate 3");
					java.util.Date utilDate = new java.util.Date();
				    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
				    ps.setInt(3, musicObject.getMusicID());
				    ps.setTimestamp(4, sqlDate);
					ps.executeUpdate();
					ps.close();
					beingPlayed = true;
					//update ratings
					PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_of_ratings= ?, rating_sum= ? " + "WHERE idmusic_table = ?");
					ps1.setInt(1, musicObject.getNumberOfRatings()+1);
					ps1.setInt(2, musicObject.getRatingSum()+3);
					ps1.setInt(3, musicObject.getMusicID());
					ps1.executeUpdate();
					ps1.close();
					musicObject.setNumberOfRatings(musicObject.getNumberOfRatings()+1);
					musicObject.setRatingSum(musicObject.getRatingSum()+3);
					double rate = musicObject.getRatingSum()/musicObject.getNumberOfRatings();
					setRating(rate);
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		fourStar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				oneStar.setIcon(fullStar);
				twoStar.setIcon(fullStar);
				threeStar.setIcon(fullStar);
				fourStar.setIcon(fullStar);
				fiveStar.setIcon(emptyStar);
				myRating = 4;
				
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setString(2, "rate 4");
					java.util.Date utilDate = new java.util.Date();
				    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
				    ps.setInt(3, musicObject.getMusicID());
				    ps.setTimestamp(4, sqlDate);
					ps.executeUpdate();
					ps.close();
					beingPlayed = true;
					//update ratings
					PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_of_ratings= ?, rating_sum= ? " + "WHERE idmusic_table = ?");
					ps1.setInt(1, musicObject.getNumberOfRatings()+1);
					ps1.setInt(2, musicObject.getRatingSum()+4);
					ps1.setInt(3, musicObject.getMusicID());
					ps1.executeUpdate();
					ps1.close();
					musicObject.setNumberOfRatings(musicObject.getNumberOfRatings()+1);
					musicObject.setRatingSum(musicObject.getRatingSum()+4);
					double rate = musicObject.getRatingSum()/musicObject.getNumberOfRatings();
					setRating(rate);
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		fiveStar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				oneStar.setIcon(fullStar);
				twoStar.setIcon(fullStar);
				threeStar.setIcon(fullStar);
				fourStar.setIcon(fullStar);
				fiveStar.setIcon(fullStar);
				myRating = 5;
				
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setString(2, "rate 5");
					java.util.Date utilDate = new java.util.Date();
				    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
				    ps.setInt(3, musicObject.getMusicID());
				    ps.setTimestamp(4, sqlDate);
					ps.executeUpdate();
					ps.close();
					beingPlayed = true;
					//update ratings
					PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_of_ratings= ?, rating_sum= ? " + "WHERE idmusic_table = ?");
					ps1.setInt(1, musicObject.getNumberOfRatings()+1);
					ps1.setInt(2, musicObject.getRatingSum()+5);
					ps1.setInt(3, musicObject.getMusicID());
					ps1.executeUpdate();
					ps1.close();
					musicObject.setNumberOfRatings(musicObject.getNumberOfRatings()+1);
					musicObject.setRatingSum(musicObject.getRatingSum()+5);
					double rate = musicObject.getRatingSum()/musicObject.getNumberOfRatings();
					setRating(rate);
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//musicObject.resumeSong();
				if (myThread == null){
					myThread = musicObject.playTheSong();
					//Sample LeapMotionThread = new Sample(myThread);
					//LeapMotionThread.run();
					if (beingPlayed) {}
					else {
						try
						{
							PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
							ps.setInt(1, LoggedInDriverGUI.userID);
							ps.setString(2, "listen");
							java.util.Date utilDate = new java.util.Date();
						    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
						    ps.setInt(3, musicObject.getMusicID());
						    ps.setTimestamp(4, sqlDate);
							ps.executeUpdate();
							ps.close();
							beingPlayed = true;
							//update number of listens
							PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_playe_count= ? " + "WHERE idmusic_table = ?");
							ps1.setInt(1, musicObject.getnumberOfPlayCounts()+1);
							ps1.setInt(2, musicObject.getMusicID());
							ps1.executeUpdate();
							ps1.close();
							musicObject.setnumberOfPlayCounts(musicObject.getnumberOfPlayCounts()+1);
							listens.setText("Listens: "+musicObject.getnumberOfPlayCounts());
						} 
						catch (SQLException e1)
						{
							e1.printStackTrace();
						}
					}
				}
				else
					myThread.resume();
			}
		});
		
		pauseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (myThread != null)
					myThread.suspend();
			}
		});
		
		forwardButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (myThread != null)
					myThread.suspend();
				if (currentSong == allSongs.size()-1)
				{
					musicObject = allSongs.get(0);
					currentSong = 0;
				}
				else
				{
					musicObject = allSongs.get(currentSong+1);
					currentSong++;
				}
				resetStuff();
				//artist.setText(musicObject.getArtistName() + " "+musicObject.getSongName());
				myThread = musicObject.playTheSong();
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setString(2, "listen");
					java.util.Date utilDate = new java.util.Date();
				    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
				    ps.setInt(3, musicObject.getMusicID());
				    ps.setTimestamp(4, sqlDate);
					ps.executeUpdate();
					ps.close();
					beingPlayed = true;
					//update number of listens
					PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_playe_count= ? " + "WHERE idmusic_table = ?");
					ps1.setInt(1, musicObject.getnumberOfPlayCounts()+1);
					ps1.setInt(2, musicObject.getMusicID());
					ps1.executeUpdate();
					ps1.close();
					musicObject.setnumberOfPlayCounts(musicObject.getnumberOfPlayCounts()+1);
					listens.setText("Listens: "+musicObject.getnumberOfPlayCounts());
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myThread != null)
					myThread.suspend();
				if (currentSong == 0)
				{
					musicObject = allSongs.get(allSongs.size()-1);
					currentSong = allSongs.size()-1;
				}
				else
				{
					musicObject = allSongs.get(currentSong-1);
					currentSong--;
				}
				resetStuff();
				//artist.setText(musicObject.getArtistName() + " "+musicObject.getSongName());
				myThread = musicObject.playTheSong();
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setString(2, "listen");
					java.util.Date utilDate = new java.util.Date();
				    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
				    ps.setInt(3, musicObject.getMusicID());
				    ps.setTimestamp(4, sqlDate);
					ps.executeUpdate();
					ps.close();
					beingPlayed = true;
					//update number of listens
					PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_playe_count= ? " + "WHERE idmusic_table = ?");
					ps1.setInt(1, musicObject.getnumberOfPlayCounts()+1);
					ps1.setInt(2, musicObject.getMusicID());
					ps1.executeUpdate();
					ps1.close();
					musicObject.setnumberOfPlayCounts(musicObject.getnumberOfPlayCounts()+1);
					listens.setText("Listens: "+musicObject.getnumberOfPlayCounts());
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});	
		enter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO comments_table (user_id,song_id,comment)" + "VALUES (?, ?, ?)");
					ps.setInt(1, LoggedInDriverGUI.userID);
					ps.setInt(2, musicObject.getMusicID());
					ps.setString(3, comment.getText());
					ps.executeUpdate();
					ps.close();
				} 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void changeSong(MusicModel m, int currentSg)
	{
		if (myThread != null)
			myThread.suspend();
		musicObject = m;
		currentSong = currentSg;
		resetStuff();
		myThread = m.playTheSong();
		try
		{
			PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO activity_feed (user_id,description,song_id,time_stamp)" + "VALUES (?, ?, ?, ?)");
			ps.setInt(1, LoggedInDriverGUI.userID);
			ps.setString(2, "listen");
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		    ps.setInt(3, musicObject.getMusicID());
		    ps.setTimestamp(4, sqlDate);
			ps.executeUpdate();
			ps.close();
			beingPlayed = true;
			//update number of listens
			PreparedStatement ps1 = (PreparedStatement) ConnectionClass.conn.prepareStatement("UPDATE music_table SET numb_playe_count= ? " + "WHERE idmusic_table = ?");
			ps1.setInt(1, musicObject.getnumberOfPlayCounts()+1);
			ps1.setInt(2, musicObject.getMusicID());
			ps1.executeUpdate();
			ps1.close();
			musicObject.setnumberOfPlayCounts(musicObject.getnumberOfPlayCounts()+1);
			listens.setText("Listens: "+musicObject.getnumberOfPlayCounts());
		} 
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
	}
	
	private void resetStuff()
	{
		artist.setText(musicObject.getArtistName() + " "+musicObject.getSongName());
		try
		{
			URL imageurl = new URL(musicObject.getAlbumPath());
			BufferedImage img = ImageIO.read(imageurl);
			ImageIcon icon = new ImageIcon(img);
			Image ResizedImage = icon.getImage().getScaledInstance(3*dim.width/4, 3*dim.width/4, Image.SCALE_SMOOTH);
			album.setIcon(new ImageIcon(ResizedImage));
		} catch (IOException e1)
		{
			
		}
		oneStar.setIcon(emptyStar);
		twoStar.setIcon(emptyStar);
		threeStar.setIcon(emptyStar);
		fourStar.setIcon(emptyStar);
		fiveStar.setIcon(emptyStar);
		//favoriteLabel.setIcon(emptyHeart);
		
		try
		{
			//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID) + " AND song_id = " + Integer.toString(musicObject.getMusicID());
			ResultSet rs = st.executeQuery(queryCheck);
			int columns = rs.getMetaData().getColumnCount();
			if (rs.next())
			{
				favoriteLabel.setIcon(fullHeart);
			}
			else
			{
				favoriteLabel.setIcon(emptyHeart);
			}
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
		double rate = musicObject.getRatingSum()/musicObject.getNumberOfRatings();
		int listens1 = musicObject.getnumberOfPlayCounts();
		listens.setText("#Listens: "+listens1);
		setRating(rate);
		mainPanel.revalidate();
		mainPanel.repaint();
		
	}
	
	private void setRating(double rate)
	{
		rating.setText("Overall Rating: "+rate+"  ");
		ratingPanel.removeAll();
		ratingPanel.add(rating);
		int i = 0;
		//System.out.println(rate);
		if (rate <= 1.4 && rate>.9)
		{
			i = 1;
		}
		else if (rate <=2.4 && rate>1.4)
		{	
			i=2;
		}
		else if (rate <=3.4 && rate>2.4)
		{
			i =3;
		}
		else if (rate <= 4.4 && rate > 3.4)
		{
			i = 4;
		}
		else if (rate >4.4)
		{
			i=5;
		}
		if (i!= 0)
		{
			for (int j = 1; j<=i; j++)
			{
				JLabel temp = new JLabel("");
				temp.setIcon(new ImageIcon("data/star2.png"));
				ratingPanel.add(temp);
			}
		}
		ratingPanel.revalidate();
		ratingPanel.repaint();
	}
	private void removePanel()
	{
			if (currentPanel == 0) 
			{
				tabPanelMain.remove(commentPanel);
			}
			else if (currentPanel == 1)
			{
				tabPanelMain.remove(ratePanel);
			}
			else if (currentPanel == 2)
			{
				tabPanelMain.remove(favoritePanel);
			}
	}
	
	public Thread getCurrentThread()
	{
		return myThread;
	}
	
	
	private void initializeComponentsGuest(){
		this.setSize(dim);
		currentPanel = 0;
		album = new JLabel("");
		album.setPreferredSize(new Dimension(3*dim.width/4, 3*dim.width/4));
		favoritePanel = new JPanel();
		commentPanel = new JPanel();
		
		ratePanel = new JPanel();
		comments = new JPanel();
		comment = new JTextField("comment");
		enter = new JButton("Enter");
		jspComments = new JScrollPane(comments);
		ratingButtons = new ArrayList<JButton>();
		setPreferredSize(new Dimension(dim.width, dim.height));
		
		try {
            URL imageurl = new URL(musicObject.getAlbumPath());
            BufferedImage img = ImageIO.read(imageurl);
            ImageIcon icon = new ImageIcon(img);
            Image ResizedImage = icon.getImage().getScaledInstance(3*dim.width/4, 3*dim.width/4, Image.SCALE_SMOOTH);
            album.setIcon(new ImageIcon(ResizedImage));
         } catch (IOException e) {
            e.printStackTrace();
         }
		
		
		artist = new JLabel(musicObject.getSongName() + " "+musicObject.getArtistName());
		artist.setPreferredSize(new Dimension(dim.width-10, dim.height/26));
		ratingPanel = new JPanel();
		ratingPanel.setPreferredSize(new Dimension(dim.width-10, dim.height/20));
		ratingPanel.setBackground(FirstPageGUI.white);
		rating = new JLabel("Overall Rating: ");
		ratingPanel.add(rating);
		listens = new JLabel("# of listens");
		listens.setPreferredSize(new Dimension(dim.width-10, dim.height/26));
		listens.setFont(FirstPageGUI.smallFont);
		listens.setForeground(FirstPageGUI.darkGrey);
		listens.setHorizontalAlignment(SwingConstants.CENTER);

		backButton = new JButton();
		
		playButton = new JButton();
		forwardButton = new JButton();
		pauseButton = new JButton();

		rateButton = new JButton();
		commentButton = new JButton();
		favoriteButton = new JButton();
	}
	
	private void createGuestGUI()
	{
		setBackground(FirstPageGUI.white);
		JPanel buttonPanel = new JPanel();
		//buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width/50, dim.height));
		buttonPanel.setPreferredSize(new Dimension(dim.width, 6*dim.height/93));
		buttonPanel.setBackground(FirstPageGUI.green);
		
		commentButton.setOpaque(false);
		commentButton.setContentAreaFilled(false);
		commentButton.setBorderPainted(false);
		commentButton.setIcon(new ImageIcon("data/commentsSmall.png"));
		commentButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		rateButton.setOpaque(false);
		rateButton.setContentAreaFilled(false);
		rateButton.setBorderPainted(false);
		rateButton.setIcon(new ImageIcon("data/starOutlineSmall.png"));
		rateButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		favoriteButton.setOpaque(false);
		favoriteButton.setContentAreaFilled(false);
		favoriteButton.setBorderPainted(false);
		favoriteButton.setIcon(new ImageIcon("data/favorite_emptySmall.png"));
		favoriteButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		buttonPanel.add(commentButton);
		buttonPanel.add(favoriteButton);
		buttonPanel.add(rateButton);
		
		JPanel bottomPanel = new JPanel();
		//bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width/50, dim.height));
		bottomPanel.setPreferredSize(new Dimension(dim.width, 6*dim.height/93));
		bottomPanel.setBackground(FirstPageGUI.green);
	
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setIcon(new ImageIcon("data/ReverseButtonSmall.png"));
		backButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		playButton.setIcon(new ImageIcon("data/playButtonSmall.png"));
		playButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		pauseButton.setOpaque(false);
		pauseButton.setContentAreaFilled(false);
		pauseButton.setBorderPainted(false);
		pauseButton.setIcon(new ImageIcon("data/pauseButtonSmall.png"));
		pauseButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		forwardButton.setOpaque(false);
		forwardButton.setContentAreaFilled(false);
		forwardButton.setBorderPainted(false);
		forwardButton.setIcon(new ImageIcon("data/forwardButtonSmall.png"));
		forwardButton.setPreferredSize(new Dimension(dim.width/5, 5*dim.height/93));
		
		bottomPanel.add(backButton);
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		bottomPanel.add(forwardButton);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(FirstPageGUI.white);
		mainPanel.setPreferredSize(new Dimension(dim.width, 50*dim.height/93));
		mainPanel.add(album);
		mainPanel.add(artist);
		artist.setBackground(FirstPageGUI.white);
		rating.setBackground(FirstPageGUI.white);
		artist.setForeground(FirstPageGUI.darkGrey);
		rating.setForeground(FirstPageGUI.darkGrey);
		artist.setFont(FirstPageGUI.smallFont);
		rating.setFont(FirstPageGUI.smallFont);
		rating.setHorizontalAlignment(SwingConstants.CENTER);
		artist.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(ratingPanel);
		mainPanel.add(listens);
		mainPanel.add(bottomPanel);
		

		resetStuff();
		//setBackground(FirstPageGUI.green);
		//mainPanel.setBackground(FirstPageGUI.darkGrey);
		add(mainPanel, BorderLayout.NORTH);
		//add(tabPanel, BorderLayout.SOUTH);
	}
}