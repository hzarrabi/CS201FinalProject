import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
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


public class IndpMusicPlayer extends JPanel{
	private Dimension dim;
	private JLabel album;
	private JLabel artist;
	private JLabel rating;
	private JButton playButton;
	private JButton pauseButton;
	//private Boolean isGuest;
	private String songName;
	private MusicModel musicObject;
	private Thread myThread;
	private JButton commentButton;
	private JButton favoriteButton;
	private JButton rateButton;
	
	TopGUI top;
	
	private JButton backPageButton;
	private ActionListener forBackButton;
	private JPanel favoritePanel;
	private JPanel ratingPanel;
	private JLabel listens;
	private JButton favoriteLabel;
	private ImageIcon fullHeart;
	private ImageIcon emptyHeart;
	private JPanel commentPanel;
	private JPanel ratePanel;
	private JPanel comments;
	private JTextField comment;
	private JScrollPane jspComments;
	private JButton enter;
	private JButton fiveStar;
	private JButton fourStar;
	private JButton threeStar;
	private JButton twoStar;
	private JButton oneStar;
	private ArrayList<JButton> ratingButtons;
	private int currentPanel;
	private JPanel tabPanelMain;
	private ImageIcon emptyStar;
	private ImageIcon fullStar;
	protected int myRating;
	
	public IndpMusicPlayer(String songTitle)
	{
		songName = songTitle;
		musicObject = LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(songName);
		backPageButton = new JButton();
		initializeComponents();
		createUserGUI();
		setEventHandlers();
		setBounds(0,0,dim.width/4, dim.height);
		setVisible(true);
	}
	
	//constructor that will be called from searchGUI, profileGUI and feedGUI
	public IndpMusicPlayer(MusicModel model, ActionListener forBackButton, Dimension d)
	{
		dim = d;
		musicObject = model;
		songName = musicObject.getSongName();
		//passed in to be the actionlistener for the back button, which will remove this
		//music player from the loggedindrivergui and go back to the previous panel
		this.forBackButton = forBackButton;
		backPageButton = new JButton("back");
		initializeComponents();
		createUserGUI();
		setEventHandlers();
		setBounds(0,0,dim.width/4, dim.height);		
		setVisible(true);

	}
	
	public void startThread()
	{
		if (myThread == null)
			myThread = musicObject.playTheSong();
		else
			myThread.resume();
	}
	
	public void stopThread()
	{
		myThread.suspend();
	}
	
	//also makes GUI... 
	private void initializeComponents(){
		currentPanel = 0;
		album = new JLabel("");
		album.setPreferredSize(new Dimension(dim.width/2, dim.width/2));
		favoritePanel = new JPanel();
		commentPanel = new JPanel();
		
		ratePanel = new JPanel();
		comments = new JPanel();
		comment = new JTextField("comment");
		enter = new JButton("Enter");
		jspComments = new JScrollPane(comment);
		ratingButtons = new ArrayList<JButton>();
		setPreferredSize(new Dimension(dim.width, dim.height));

		artist = new JLabel("");
		artist.setPreferredSize(new Dimension(dim.width-10, dim.height/26));
		ratingPanel = new JPanel();
		ratingPanel.setPreferredSize(new Dimension(dim.width-10, dim.height/20));
		ratingPanel.setBackground(FirstPageGUI.white);
		rating = new JLabel("");
		ratingPanel.add(rating);
		listens = new JLabel("");
		listens.setPreferredSize(new Dimension(dim.width-10, dim.height/26));
		listens.setFont(FirstPageGUI.smallFont);
		listens.setForeground(FirstPageGUI.darkGrey);
		listens.setHorizontalAlignment(SwingConstants.CENTER);
		playButton = new JButton();
		pauseButton = new JButton();

		rateButton = new JButton();
		commentButton = new JButton();
		favoriteButton = new JButton();
		emptyStar = new ImageIcon("data/starOutline.png");
		fullStar = new ImageIcon("data/starFullBlack.png");
	}
	
	//makes GUI
	private void createUserGUI()
	{
		setBackground(FirstPageGUI.white);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(dim.width, dim.height/12));
		buttonPanel.setBackground(FirstPageGUI.green);
		
		commentButton.setOpaque(false);
		commentButton.setContentAreaFilled(false);
		commentButton.setBorderPainted(false);
		commentButton.setIcon(new ImageIcon("data/comments1.png"));
		commentButton.setPreferredSize(new Dimension(dim.width/5, dim.height/14));
		
		rateButton.setOpaque(false);
		rateButton.setContentAreaFilled(false);
		rateButton.setBorderPainted(false);
		rateButton.setIcon(new ImageIcon("data/rating1.png"));
		rateButton.setPreferredSize(new Dimension(dim.width/5, dim.height/15));
		
		favoriteButton.setOpaque(false);
		favoriteButton.setContentAreaFilled(false);
		favoriteButton.setBorderPainted(false);
		favoriteButton.setIcon(new ImageIcon("data/favorite_empty1.png"));
		favoriteButton.setPreferredSize(new Dimension(dim.width/5, dim.height/15));
		
		buttonPanel.add(commentButton);
		buttonPanel.add(favoriteButton);
		buttonPanel.add(rateButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(dim.width, dim.height/13));
		bottomPanel.setBackground(FirstPageGUI.green);
		
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		playButton.setIcon(new ImageIcon("data/playButton.png"));
		playButton.setPreferredSize(new Dimension(dim.width/5, dim.height/15));
		
		pauseButton.setOpaque(false);
		pauseButton.setContentAreaFilled(false);
		pauseButton.setBorderPainted(false);
		pauseButton.setIcon(new ImageIcon("data/pauseButton.png"));
		pauseButton.setPreferredSize(new Dimension(dim.width/5, dim.height/17));
		
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		bottomPanel.add(backPageButton);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(FirstPageGUI.white);
		mainPanel.setPreferredSize(new Dimension(dim.width, 56*dim.height/93));
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
		favoritePanel.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		favoriteLabel.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		favoritePanel.add(favoriteLabel);
		
		emptyHeart = new ImageIcon("data/heartOutline.png");
		fullHeart = new ImageIcon("data/fullHeartBlack.png");
		favoriteLabel.setOpaque(false);
		favoriteLabel.setContentAreaFilled(false);
		favoriteLabel.setBorderPainted(false);
		favoriteLabel.setIcon(emptyHeart);
		
		commentPanel = new JPanel();
		commentPanel.setPreferredSize(new Dimension(dim.width, 32*dim.height/115));
		ratePanel.setPreferredSize(new Dimension(dim.width, 2*dim.height/24));
		comments.setPreferredSize(new Dimension(dim.width, 5*dim.height/24));
		comment.setPreferredSize(new Dimension(3*dim.width/5, dim.height/24));
		jspComments.setPreferredSize(new Dimension(dim.width, 5*dim.height/24));
		favoritePanel.setBackground(FirstPageGUI.white);
		commentPanel.setBackground(FirstPageGUI.white);
		comments.setBackground(FirstPageGUI.white);
		ratePanel.setBackground(FirstPageGUI.white);
		enter.setPreferredSize(new Dimension(dim.width/5, dim.height/24));
		
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
		other.setPreferredSize(new Dimension(dim.width, dim.height/17));
		other.add(enter, BorderLayout.WEST);
		other.add(comment, BorderLayout.EAST);
		commentPanel.add(jspComments, BorderLayout.CENTER);
		commentPanel.add(other, BorderLayout.SOUTH);

		JPanel tabPanel = new JPanel();
		
		
		tabPanel.setPreferredSize(new Dimension(dim.width, 11*dim.height/24));
		tabPanel.setBackground(FirstPageGUI.white);
		tabPanelMain = new JPanel();
		tabPanelMain.setPreferredSize(new Dimension(dim.width, 7*dim.height/25));
		tabPanelMain.add(commentPanel, BorderLayout.CENTER);
		tabPanelMain.setBackground(FirstPageGUI.white);
		tabPanel.add(tabPanelMain, BorderLayout.CENTER);
		tabPanel.add(buttonPanel, BorderLayout.SOUTH);
		resetStuff();
		add(mainPanel, BorderLayout.CENTER);
		add(tabPanel, BorderLayout.SOUTH);
	}
	
	private void setEventHandlers(){
		
		//plays music
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				myThread.resume();
			}
		});
		
		//pauses music
		pauseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				myThread.suspend();
			}
		});

		backPageButton.addActionListener(forBackButton);
		
		//this sets the favoriting icon appropriately and changes the database depending on whether the 
		//user favorited or unfavorited the song
		favoriteLabel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				//String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(this.userID);
				try
				{
				ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
				
				Statement st = ConnectionClass.conn.createStatement();
				//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
				String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID);
				ResultSet rs = st.executeQuery(queryCheck);
				int columns = rs.getMetaData().getColumnCount();
//				if (rs.next())
//				{
//					favoriteLabel.setIcon(fullHeart);
//				}
//				else
//				{
//					favoriteLabel.setIcon(emptyHeart);
//				}
				
				if (favoriteLabel.getIcon() == emptyHeart)
				{
					favoriteLabel.setIcon(fullHeart);
					if (!musicObject.getFavoritedBool())
					{

							if (!rs.next()) 
							{
								try
								{
									PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO favorite_songs (user_id, song_id)" + "VALUES (?, ?)");
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
						musicObject.setFavoritedBool(true);
					}
				}
				else
				{
					favoriteLabel.setIcon(emptyHeart);
					if (musicObject.getFavoritedBool())
					{
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
						musicObject.setFavoritedBool(false);
					}
				}
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
			
		});
		
		//favoriteButton is on my custom tabbedPane. for switching to the favorite panel
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
					ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
					
					Statement st = ConnectionClass.conn.createStatement();
					//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
					String queryCheck = "SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID);
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
		
		//rateButton is on my custom tabbedPane. for switching to the rate panel
		rateButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentPanel = 1;
				tabPanelMain.add(ratePanel, BorderLayout.SOUTH);
				tabPanelMain.revalidate();
	            tabPanelMain.repaint();
				
			}
		});
		
		//commentButton is on my custom tabbedPane. for switching to the comment panel
		commentButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentPanel = 0;
				tabPanelMain.add(commentPanel);
				tabPanelMain.revalidate();
	            tabPanelMain.repaint();
				
			}
		});
		
		//the following actionlisteners are for setting the rating icons appropriately determined
		//by the rating the user gives the song
		oneStar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				oneStar.setIcon(fullStar);
				twoStar.setIcon(emptyStar);
				threeStar.setIcon(emptyStar);
				fourStar.setIcon(emptyStar);
				fiveStar.setIcon(emptyStar);
				myRating = 1;
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
			}
			
		});
		
	}
	
	//when a song is changed, this function resets all the information to display the info
	//of this new song
	private void resetStuff()
	{
		artist.setText(musicObject.getArtistName() + " "+musicObject.getSongName());
		try
		{
			URL imageurl = new URL(musicObject.getAlbumPath());
			BufferedImage img = ImageIO.read(imageurl);
			ImageIcon icon = new ImageIcon(img);
			Image ResizedImage = icon.getImage().getScaledInstance(dim.width/2, dim.width/2, Image.SCALE_SMOOTH);
			album.setIcon(new ImageIcon(ResizedImage));
		} catch (IOException e1)
		{
			
		}
		oneStar.setIcon(emptyStar);
		twoStar.setIcon(emptyStar);
		threeStar.setIcon(emptyStar);
		fourStar.setIcon(emptyStar);
		fiveStar.setIcon(emptyStar);
		favoriteLabel.setIcon(emptyHeart);
		double rate = musicObject.getRatingSum()/musicObject.getNumberOfRatings();
		int listens1 = musicObject.getnumberOfPlayCounts();
		listens.setText("#Listens: "+listens1);
		setRating(rate);
		
	}
	
	//sets the star icons for the overall rating of the song
	private void setRating(double rate)
	{
		rating.setText("Overall Rating: "+rate+"  ");
		ratingPanel.removeAll();
		ratingPanel.add(rating);
		int i = 0;
		if (rate <= 1.4 && i>.9) { i = 1; }
		else if (rate <=2.4) { i=2; }
		else if (rate <=3.4) { i =3; }
		else if (rate <= 4.4) { i = 4; }
		else { i=5; }
		for (int j = 0; j<=i; j++)
		{
			JLabel temp = new JLabel("");
			temp.setIcon(new ImageIcon("data/star2.png"));
			ratingPanel.add(temp);
		}
		ratingPanel.revalidate();
		ratingPanel.repaint();
	}
	
	//removes the current panel (this is for tabbedPane functionality)
	private void removePanel()
	{
			if (currentPanel == 0) { tabPanelMain.remove(commentPanel); }
			else if (currentPanel == 1) { tabPanelMain.remove(ratePanel); }
			else if (currentPanel == 2) { tabPanelMain.remove(favoritePanel); }
	}
}