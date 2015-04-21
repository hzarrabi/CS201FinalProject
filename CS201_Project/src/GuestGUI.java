import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;


public class GuestGUI extends JFrame{
	
	private JTabbedPane jtp;
	private Dimension dim;
	private TopRatedGUI trg;
	private TopListenedGUI tlg;
	private JLabel title;
	private JButton logout;
	private JPanel titlePanel;
	JPanel bottomColor;
	static MusicLibrary sharedMusicLibrary;
	private int currentJpanel;
	private Color myColor;
	private JPanel mainPanel;
	private JButton trgButton;
	private JButton tlgButton;
	private JComponent buttonPanel;
	private MusicPlayer musicPlayerTopRated;
	private MusicPlayer musicPlayerTopListened;
	private JScrollPane trgScroll;
	private JScrollPane tlgScroll;
	
	public GuestGUI()
	{
//this.firstPage = firstPage;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				//firstPage.setVisible(true);
				new FirstPageGUI();
				
				GuestGUI.this.dispose();	
			}

		});
		//testButton = new JButton("Search");
		//this.userID=userID;
		
		try{
			sharedMusicLibrary = new MusicLibrary();
		}catch(Exception e){
			
		}

		//connect();
		initializeComponents();
		createGUI();
		setEventHandlers();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		setResizable(false);
	}
	
	private void initializeComponents()
	{
		currentJpanel = 0;
		//fg = new FeedGUI();
		myColor = FirstPageGUI.color;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//testField.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		//testField.setEditable(true);
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		trgButton = new JButton();
		tlgButton = new JButton();
		//mpgButton = new JButton();
		//feedButton = new JButton();
		//searchButton = new JButton();
		trgButton.setIcon(new ImageIcon("data/star1.png"));
		tlgButton.setIcon(new ImageIcon("data/headphones1.png"));
		//mpgButton.setIcon(new ImageIcon("data/profile.png"));
		//feedButton.setIcon(new ImageIcon("data/home.png"));
		//searchButton.setIcon(new ImageIcon("data/search.png"));
		trgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		//mpgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		//searchButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		//feedButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setBackground(myColor);
		tlgButton.setOpaque(false);
		tlgButton.setContentAreaFilled(false);
		tlgButton.setBorderPainted(false);
		
		trgButton.setOpaque(false);
		trgButton.setContentAreaFilled(false);
		trgButton.setBorderPainted(false);
		
		logout = new JButton("Logout");
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(dim.width/3, dim.height/15));
		buttonPanel.setBackground(myColor);
		//buttonPanel.add(feedButton);
		//buttonPanel.add(mpgButton);
		buttonPanel.add(tlgButton);
		buttonPanel.add(trgButton);
		//buttonPanel.add(searchButton);



		trg = new TopRatedGUI(new Dimension(3*dim.width/24, 15*dim.height/20), new Dimension(11*dim.width/48, 15*dim.height/20));
		tlg = new TopListenedGUI(new Dimension(3*dim.width/24, 15*dim.height/20), new Dimension(11*dim.width/48, 15*dim.height/20));
		//mpg = new ProfileGUI(new Dimension(dim.width/3, 15*dim.height/20), "current user", userID,ConnectionClass.conn);

		musicPlayerTopRated = trg.initPlayer();
		musicPlayerTopListened = tlg.initPlayer();

		trgScroll = new JScrollPane(trg);
		tlgScroll = new JScrollPane(tlg);
		//fgScroll = new JScrollPane(fg);
		//notifications = new JLabel("notifications");
		trgScroll.setPreferredSize(new Dimension(dim.width/12, 15*dim.height/20));
		tlgScroll.setPreferredSize(new Dimension(dim.width/12, 15*dim.height/20));
		//fgScroll.setPreferredSize(new Dimension(dim.width/15, 15*dim.height/20));
		trgScroll.setBorder(null);
		tlgScroll.setBorder(null);
		mainPanel.add(tlgScroll, BorderLayout.WEST);
		mainPanel.add(musicPlayerTopListened, BorderLayout.CENTER);
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(myColor);
		bottomColor.add(logout);
		//bottomColor.add(notifications);
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
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FirstPageGUI();
			}
		});
		
		trgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				mainPanel.add(trgScroll, BorderLayout.WEST);
				mainPanel.add(musicPlayerTopRated, BorderLayout.EAST);
				currentJpanel = 1;
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

				//mainPanel.add(new MusicPlayer("Headlines"), BorderLayout.CENTER);
				//mainPanel.add(new MusicPlayer("Headlines"), BorderLayout.CENTER);
				currentJpanel = 0;
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
	}
	
	private void removePanel()
	{
			if (currentJpanel == 1) 
			{
				mainPanel.remove(trgScroll);
				mainPanel.remove(musicPlayerTopRated);
				trg.stopSong();
			}
			else if (currentJpanel == 0)
			{
				mainPanel.remove(tlgScroll);
				mainPanel.remove(musicPlayerTopListened);
				tlg.stopSong();
			}
	}
}