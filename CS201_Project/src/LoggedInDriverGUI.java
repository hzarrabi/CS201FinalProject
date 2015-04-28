import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private Boolean hasIndpFrame;
	private SearchGUI searchGUI;
	private JButton logout;
	private Thread currentSongThread;
	//private JLabel notifications;
	private JScrollPane trgScroll;
	private JScrollPane tlgScroll;
	private JScrollPane mpgScroll;
	private JLabel loading;
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
	static String username;
	private JPanel currentGUI;
	private JPanel previousGUI;
	private JButton refreshButton;
	
	//this is a either a ProfileGUI or a IndpMusicPlayer
	private JPanel tempGUI;
	
	private JTextField testField;
	private JButton testButton;
	private String searchText;
	JPanel bottomColor;
	
	static int numFavoriteSongs = 0;
	static Vector<Integer> songID = new Vector<Integer> ();
	static Vector<MusicModel> favoriteSongNames = new Vector<MusicModel> ();
	
	String [] sql_queries = {"SELECT * from user_table WHERE username = ?", 
			"SELECT * from music_table WHERE song_name = ?",
			"SELECT * from music_table WHERE artist_name = ?"};
	
	static int userID;//unique user ID of the user that logged in
	//private Connection conn;//for database connection
	
	static MusicLibrary sharedMusicLibrary;
	private MusicPlayer musicPlayerTopRated;
	private MusicPlayer musicPlayerTopListened;
	
	private IndpMusicPlayer currentPlayer;
	//private FirstPageGUI firstPage;
	private int currentPanelNum;
	
	public LoggedInDriverGUI(int userID)
	{		
		super("Home Screen");
		this.userID=userID;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		dim = new Dimension(dim.width, dim.height-100);
		testField = new JTextField();
		//this.firstPage = firstPage;
		loading = new JLabel("", SwingConstants.CENTER);
		loading.setPreferredSize(new Dimension(9*dim.width/32, 4*dim.height/5));
		loading.setIcon(new ImageIcon("data/loading5.gif"));
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width/3, 35*dim.height/40));
		myColor = FirstPageGUI.color;
		mainPanel.setBackground(FirstPageGUI.darkGrey);
		mainPanel.add(loading, BorderLayout.CENTER);
		add(mainPanel, BorderLayout.CENTER);
		setBackground(FirstPageGUI.darkGrey);
		setBounds(0,0,dim.width/3, dim.height);
		setResizable(false);
		setSize(dim.width/3, dim.height);
		setVisible(true);
		try{
			sharedMusicLibrary = new MusicLibrary();
		}catch(Exception e){
			
		}

		//connect();
		initializeComponents();
		createGUI();
		setEventHandlers();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				//firstPage.setVisible(true);
				Object[] options = {"Yes",
                	"No"};
				int n = JOptionPane.showOptionDialog(LoggedInDriverGUI.this,
			    "Would you like to logout?",
			    "",
			    JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
				if (n==0) {
					MusicPlayer.stopThread();
					new FirstPageGUI();
					LoggedInDriverGUI.this.dispose();
				}
				if (n==1) {}
			}

		});
		hasIndpFrame = false;
		testButton = new JButton("Search");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setResizable(false);
		setVisible(true);
	}
	
	private void initializeComponents()
	{
		currentJpanel = 0;
		trgButton = new JButton();
		tlgButton = new JButton();
		mpgButton = new JButton();
		feedButton = new JButton();
		searchButton = new JButton();
		fg = new FeedGUI(this, new Dimension(dim.width/3, dim.height), new Dimension(dim.width/3, 31*dim.height/40));
		fgScroll = new JScrollPane(fg, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fgScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		fgScroll.setBackground(FirstPageGUI.darkGrey);
		fgScroll.setPreferredSize(new Dimension(dim.width/3, 35*dim.height/40));
		
		setVisible(true);
		testField.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		testField.setEditable(true);
		//mainPanel.setBackground(FirstPageGUI.color);
		trgButton.setIcon(new ImageIcon("data/star1.png"));
		tlgButton.setIcon(new ImageIcon("data/head1.png"));
		mpgButton.setIcon(new ImageIcon("data/profile.png"));
		feedButton.setIcon(new ImageIcon("data/home.png"));
		searchButton.setIcon(new ImageIcon("data/search.png"));
		trgButton.setDisabledIcon(new ImageIcon("data/star1.png"));
		tlgButton.setDisabledIcon(new ImageIcon("data/head1.png"));
		mpgButton.setDisabledIcon(new ImageIcon("data/profile.png"));
		feedButton.setDisabledIcon(new ImageIcon("data/home.png"));
		searchButton.setDisabledIcon(new ImageIcon("data/search.png"));
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

		buttonPanel.setBackground(myColor);
		buttonPanel.add(feedButton);
		buttonPanel.add(mpgButton);
		buttonPanel.add(tlgButton);
		buttonPanel.add(trgButton);
		buttonPanel.add(searchButton);

		currentPanelNum = 0;

		trg = new TopRatedGUI(this, new Dimension(6*dim.width/96, dim.height), new Dimension(11*dim.width/48, 38*dim.height/40), false);
		tlg = new TopListenedGUI(this, new Dimension(6*dim.width/96, dim.height), new Dimension(11*dim.width/48, 38*dim.height/40), false);
		mpg = new ProfileGUI(this, new Dimension(dim.width/3, 37*dim.height/40), "current user", userID);
		searchGUI = new SearchGUI(new Dimension(dim.width/3, 35*dim.height/40), userID, ConnectionClass.conn, this);

		trgScroll = new JScrollPane(trg);
		trgScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		tlgScroll = new JScrollPane(tlg);
		tlgScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		//fgScroll = new JScrollPane(fg);
		//fgScroll.setBackground(FirstPageGUI.darkGrey);
		//notifications = new JLabel("notifications");
		trgScroll.setPreferredSize(new Dimension(18*dim.width/192, 38*dim.height/40));
		tlgScroll.setPreferredSize(new Dimension(18*dim.width/192, 38*dim.height/40));
//		fgScroll.setPreferredSize(new Dimension(dim.width/3, 35*dim.height/40));
		trgScroll.setBorder(null);
		tlgScroll.setBorder(null);
		//mainPanel.add(fgScroll, BorderLayout.CENTER);
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(myColor);
		logout.setBorder(new RoundedBorder());
		logout.setBackground(FirstPageGUI.darkGrey);
		logout.setForeground(FirstPageGUI.white);
		logout.setFont(FirstPageGUI.smallFont);
		bottomColor.add(logout);
		logout.setOpaque(true);
		tlgButton.setEnabled(false);
		trgButton.setEnabled(false);
		mpgButton.setEnabled(false);
		feedButton.setEnabled(false);
		searchButton.setEnabled(false);
		//bottomColor.add(notifications);
		//mainPanel.setBackground(FirstPageGUI.color);
	}
	
	class StartThread extends Thread{
		public void run(){
			fg.make();
			mpg.make();
			username = mpg.getName();
			trg.fillButtons();
			tlg.fillButtons();
			musicPlayerTopRated = trg.initPlayer();
			musicPlayerTopListened = tlg.initPlayer();
			mainPanel.remove(loading);
			mainPanel.setBackground(FirstPageGUI.color);
			mainPanel.add(fgScroll, BorderLayout.CENTER);
			tlgButton.setEnabled(true);
			trgButton.setEnabled(true);
			mpgButton.setEnabled(true);
			feedButton.setEnabled(true);
			searchButton.setEnabled(true);
			mainPanel.revalidate();
			mainPanel.repaint();
		}
	}
	
	public void addCurrent(JPanel currentP)
	{
		if (currentGUI != null)
		{
			mainPanel.remove(currentGUI);
		}
		else
		{
			removePanel();
		}
		currentGUI = currentP;
		//removePanel();
		hasIndpFrame = true;
		mainPanel.add(currentGUI);
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	private void createGUI()
	{
		add(buttonPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
		setBounds(0,0,dim.width/3, dim.height);
		setResizable(false);
		setVisible(true);
		new StartThread().start();
	}
	
	private void setEventHandlers()
	{
		
		trgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hasIndpFrame)
				{
					mainPanel.remove(currentGUI);
					musicPlayerTopRated = trg.refresh();
					mainPanel.add(trgScroll, BorderLayout.WEST);
					mainPanel.add(musicPlayerTopRated, BorderLayout.EAST);
					currentJpanel = 3;
					hasIndpFrame = false;
					currentGUI = null;
		            mainPanel.revalidate();
		            mainPanel.repaint();
				}
				else
				{
					removePanel();
					musicPlayerTopRated = trg.refresh();
					mainPanel.add(trgScroll, BorderLayout.WEST);
					mainPanel.add(musicPlayerTopRated, BorderLayout.EAST);
					currentJpanel = 3;
		            mainPanel.revalidate();
		            mainPanel.repaint();
				}
			}
		});
		
		tlgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				if (hasIndpFrame)
				{
					mainPanel.remove(currentGUI);
					musicPlayerTopListened = tlg.refresh();
					mainPanel.add(tlgScroll, BorderLayout.WEST);
					mainPanel.add(musicPlayerTopListened, BorderLayout.EAST);
					currentJpanel = 4;
					hasIndpFrame = false;
					currentGUI = null;
		            mainPanel.revalidate();
		            mainPanel.repaint();
				}
				else
				{
					musicPlayerTopListened = tlg.refresh();
					mainPanel.add(tlgScroll, BorderLayout.WEST);
					mainPanel.add(musicPlayerTopListened, BorderLayout.EAST);
					currentJpanel = 4;
		            mainPanel.revalidate();
		            mainPanel.repaint();
				}
			}
		});
		mpgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hasIndpFrame)
				{
					mainPanel.remove(currentGUI);
					mpg.refresh();
					hasIndpFrame = false;
					currentGUI = null;
					mainPanel.add(mpg, BorderLayout.CENTER);
		            mainPanel.revalidate();
		            mainPanel.repaint();
					currentJpanel = 1;
				}
				else
				{
					removePanel();
					mpg.refresh();
					mainPanel.add(mpg, BorderLayout.CENTER);
		            mainPanel.revalidate();
		            mainPanel.repaint();
					currentJpanel = 1;
				}
			}
		});
		feedButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hasIndpFrame)
				{
					mainPanel.remove(currentGUI);
					currentJpanel = 0;
					hasIndpFrame = false;
					currentGUI = null;
					mainPanel.add(fgScroll, BorderLayout.CENTER);
					mainPanel.revalidate();
					mainPanel.repaint();
				}
				else
				{
					removePanel();
					currentJpanel = 0;
					mainPanel.add(fgScroll, BorderLayout.CENTER);
					mainPanel.revalidate();
					mainPanel.repaint();
				}
			}
		});
		searchButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (hasIndpFrame)
				{
					mainPanel.remove(currentGUI);
					mainPanel.add(searchGUI, BorderLayout.CENTER);
					mainPanel.revalidate();
		            mainPanel.repaint();
		            currentJpanel = 2;
		            hasIndpFrame = false;
		            currentGUI = null;
				}
				else
				{
					removePanel();
					mainPanel.add(searchGUI, BorderLayout.CENTER);
					mainPanel.revalidate();
					mainPanel.repaint();
					currentJpanel = 2;
				}
			}
			
		});
	
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Yes",
	                    "No"};
				int n = JOptionPane.showOptionDialog(LoggedInDriverGUI.this,
			    "Would you like to logout?",
			    "",
			    JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
				if (n==0) {
					MusicPlayer.stopThread();
					dispose();
					new FirstPageGUI();	
				}
				if (n==1) {}
			}
		});
		
//		refreshButton.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
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
				mainPanel.remove(searchGUI);
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

