import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.PreparedStatement;


public class LoggedInDriverGUI extends JFrame{
	private Dimension dim;
	private TopRatedGUI trg;
	private TopListenedGUI tlg;
	private FeedGUI fg;
	private ProfileGUI mpg;
	private JButton logout;
	private JLabel notifications;
	private JScrollPane trgScroll;
	private JScrollPane tlgScroll;
	private JScrollPane mpgScroll;
	private JScrollPane fgScroll;
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JButton trgButton;
	private JButton tlgButton;
	private JButton mpgButton;
	private JButton feedButton;
	private JButton searchButton;
	private int currentJpanel;
	private Color myColor;
	
	private JTextField testField;
	private JButton testButton;
	private String searchText;
	JPanel bottomColor;
	
	String [] sql_queries = {"SELECT * from user_table WHERE username = ?", 
			"SELECT * from music_table WHERE song_name = ?",
			"SELECT * from music_table WHERE artist_name = ?"};
	
	int userID;
	
	static MusicLibrary sharedMusicLibrary;
	private MusicPlayer musicPlayerTopRated;
	private MusicPlayer musicPlayerTopListened;
	
	public LoggedInDriverGUI(int userID)
	{		
		super("Home Screen");
		testField = new JTextField();
		testButton = new JButton("Search");
		this.userID=userID;
		
		try{
			sharedMusicLibrary = new MusicLibrary();
		}catch(Exception e){
			
		}

		initializeComponents();
		createGUI();
		setEventHandlers();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		setResizable(false);
	}
	
	private void initializeComponents()
	{
		currentJpanel = 0;
		fg = new FeedGUI();
		myColor = FirstPageGUI.color;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		testField.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		testField.setEditable(true);
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		trgButton = new JButton();
		tlgButton = new JButton();
		mpgButton = new JButton();
		feedButton = new JButton();
		searchButton = new JButton();
		trgButton.setIcon(new ImageIcon("data/star1.png"));
		tlgButton.setIcon(new ImageIcon("data/headphones1.png"));
		mpgButton.setIcon(new ImageIcon("data/profile.png"));
		feedButton.setIcon(new ImageIcon("data/home.png"));
		searchButton.setIcon(new ImageIcon("data/search.png"));
		trgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		mpgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		searchButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		feedButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setBackground(myColor);
		tlgButton.setOpaque(false);
		tlgButton.setContentAreaFilled(false);
		tlgButton.setBorderPainted(false);
		trgButton.setOpaque(false);
		trgButton.setContentAreaFilled(false);
		trgButton.setBorderPainted(false);
		mpgButton.setOpaque(false);
		mpgButton.setContentAreaFilled(false);
		mpgButton.setBorderPainted(false);
		searchButton.setOpaque(false);
		searchButton.setContentAreaFilled(false);
		searchButton.setBorderPainted(false);
		feedButton.setOpaque(false);
		feedButton.setContentAreaFilled(false);
		feedButton.setBorderPainted(false);
		
		logout = new JButton("Logout");
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(dim.width/3, dim.height/15));
		buttonPanel.setBackground(myColor);
		buttonPanel.add(feedButton);
		buttonPanel.add(mpgButton);
		buttonPanel.add(tlgButton);
		buttonPanel.add(trgButton);
		buttonPanel.add(searchButton);

		trg = new TopRatedGUI(new Dimension(dim.width/12, 15*dim.height/20), new Dimension(2*dim.width/12, 15*dim.height/20));
		tlg = new TopListenedGUI(new Dimension(dim.width/12, 15*dim.height/20), new Dimension(2*dim.width/12, 15*dim.height/20));
		mpg = new ProfileGUI(new Dimension(dim.width/3, 15*dim.height/20), "current user", 0);
		musicPlayerTopRated = trg.initPlayer();
		musicPlayerTopListened = tlg.initPlayer();

		trgScroll = new JScrollPane(trg);
		tlgScroll = new JScrollPane(tlg);
		fgScroll = new JScrollPane(fg);
		notifications = new JLabel("notifications");
		trgScroll.setPreferredSize(new Dimension(dim.width/12, 15*dim.height/20));
		tlgScroll.setPreferredSize(new Dimension(dim.width/12, 15*dim.height/20));
		fgScroll.setPreferredSize(new Dimension(dim.width/15, 15*dim.height/20));
		mainPanel.add(fgScroll, BorderLayout.CENTER);
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(myColor);
		bottomColor.add(logout);
		bottomColor.add(notifications);
		mainPanel.setBackground(FirstPageGUI.white);
	}
	
	private void createGUI()
	{
		setLayout(new FlowLayout());
		add(buttonPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
	}
	
	private void setEventHandlers()
	{
		
		trgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				mainPanel.add(trgScroll, BorderLayout.WEST);
				mainPanel.add(musicPlayerTopRated, BorderLayout.EAST);
				trg.startSong();
				currentJpanel = 3;
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
		
		tlgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				mainPanel.add(tlgScroll, BorderLayout.WEST);
				mainPanel.add(musicPlayerTopListened, BorderLayout.EAST);
				tlg.startSong();
				//mainPanel.add(new MusicPlayer("Headlines"), BorderLayout.CENTER);
				//mainPanel.add(new MusicPlayer("Headlines"), BorderLayout.CENTER);
				currentJpanel = 4;
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
		mpgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				mainPanel.add(mpg, BorderLayout.CENTER);
	            mainPanel.revalidate();
	            mainPanel.repaint();
				currentJpanel = 1;
			}
		});
		feedButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentJpanel = 0;
			    mainPanel.add(fgScroll, BorderLayout.CENTER);
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
		searchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentJpanel = 2;
				mainPanel.add(testField);
				mainPanel.add(testButton);
				//mainPanel.add(musicPlayerTopListened);
				mainPanel.revalidate();
	            mainPanel.repaint();
				
			}
			
		});
		testButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				searchText = testField.getText();
				Connection conn; 
				String dburl = "jdbc:mysql://104.236.176.180:3306/cs201";
				String userName = "cs201";
				String passWord = "manishhostage";
				try {
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
					Statement st = conn.createStatement();
					String queryCheck = "";
					boolean check_found = false;

					//check for users
					queryCheck = sql_queries[0];
					PreparedStatement ps = (PreparedStatement) conn.prepareStatement(queryCheck);
					ps.setString(1, searchText);
					ResultSet rs = ps.executeQuery();
					if(rs.absolute(1))
					{	
						System.out.println("Username " + searchText +" exists!");
						check_found = true;
					}
					//check for songs
					queryCheck = sql_queries[1];
					ps= (PreparedStatement) conn.prepareStatement(queryCheck);
					ps.setString(1, searchText);
					rs = ps.executeQuery();
					if(rs.absolute(1))
					{	
						System.out.println("Song " + searchText + " exists!");
						check_found = true;
					}
					//check for artists
					queryCheck = sql_queries[2];
					ps= (PreparedStatement) conn.prepareStatement(queryCheck);
					ps.setString(1, searchText);
					rs = ps.executeQuery();
					if(rs.absolute(1))
					{	
						System.out.println("Artist " + searchText + " exists!");
						check_found = true;
					}
				
					if (!check_found)
					{
						System.out.println("Not found");
					}
				
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}			}

		});
		
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FirstPageGUI();
			}
		});
	}
	
	private void removePanel()
	{
			if (currentJpanel == 0) 
			{
				mainPanel.remove(fgScroll);
			}
			else if (currentJpanel == 1)
				mainPanel.remove(mpg);
			else if (currentJpanel == 2)
			{
				mainPanel.remove(testField);
				mainPanel.remove(testButton);
			}
			else if (currentJpanel ==3)
			{
				mainPanel.remove(trgScroll);
				mainPanel.remove(musicPlayerTopRated);
				trg.stopSong();
			}
			else if (currentJpanel == 4)
			{
				mainPanel.remove(tlgScroll);
				mainPanel.remove(musicPlayerTopListened);
				tlg.stopSong();
			}
	}
			
}

