import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class IndpMusicPlayer extends JPanel{
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
	//private Boolean isGuest;
	private String songName;
	private MusicModel musicObject;
	private Thread myThread;
	JButton commentButton;
	JButton favoriteButton;
	JButton rateButton;
	
	TopGUI top;
	
	JButton backPageButton;
	
	private ArrayList<JButton> allButtons;
	private ArrayList<MusicModel> allSongs;
	private int currentSong;
	
	public IndpMusicPlayer(String songTitle)
	{
		songName = songTitle;
		musicObject = LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(songName);
		initializeComponents();
		createGUI();
		backPageButton = new JButton("back a  page");
		setEventHandlers();
		setBounds(0,0,dim.width/4, dim.height);
		setVisible(true);
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		//starting a thread for the song
		myThread = musicObject.playTheSong();

	}
	
	public IndpMusicPlayer(TopGUI backOne, Dimension d, ArrayList<JButton> buttons, ArrayList<MusicModel> songs, int currentSong)
	{
		this.currentSong = currentSong;
		this.allButtons = buttons;
		dim = d;
		backPageButton = new JButton("back a page");
		top = backOne;
		this.allSongs = songs;
		musicObject = allSongs.get(currentSong);
		songName = musicObject.getSongName();
		initializeComponents();
		createGUIWithForwardAndBack();
		setEventHandlers();
		setVisible(true);
		repaint();
		myThread = musicObject.playTheSong();

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
	private void initializeComponents(){
		commentButton = new JButton();
		favoriteButton = new JButton();
		rateButton = new JButton();
		rateButton.setIcon(new ImageIcon("data/star1.png"));
		commentButton.setIcon(new ImageIcon("data/headphones1.png"));
		favoriteButton.setIcon(new ImageIcon("data/profile.png"));
		commentButton.setPreferredSize(new Dimension(dim.width/20, dim.height/16));
		rateButton.setPreferredSize(new Dimension(dim.width/20, dim.height/16));
		favoriteButton.setPreferredSize(new Dimension(dim.width/20, dim.height/16));
		//tlgButton.setBackground(FirstPageGUI.green);
		commentButton.setOpaque(false);
		commentButton.setContentAreaFilled(false);
		commentButton.setBorderPainted(false);
		rateButton.setOpaque(false);
		rateButton.setContentAreaFilled(false);
		rateButton.setBorderPainted(false);
		favoriteButton.setOpaque(false);
		favoriteButton.setContentAreaFilled(false);
		favoriteButton.setBorderPainted(false);
		album = new JLabel("");
		album.setPreferredSize(new Dimension(dim.width-10, dim.width-10));
		setPreferredSize(new Dimension(dim.width, dim.height));
		
		try {
            URL imageurl = new URL(musicObject.getAlbumPath());
            BufferedImage img = ImageIO.read(imageurl);
            ImageIcon icon = new ImageIcon(img);
            Image ResizedImage = icon.getImage().getScaledInstance(dim.width-10, dim.width-10, Image.SCALE_SMOOTH);
            album.setIcon(new ImageIcon(ResizedImage));
         } catch (IOException e) {
            e.printStackTrace();
         }
		
		artist = new JLabel(musicObject.getSongName());
		artist.setPreferredSize(new Dimension(dim.width-10, dim.height/15));
		rating = new JLabel("Rating and # of Listens");
		backButton = new JButton("back");
		playButton = new JButton("Play");
		forwardButton = new JButton("forward");
		pauseButton = new JButton("Pause");

	}
	
	private void createGUIWithForwardAndBack()
	{
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(dim.width, dim.height/15));
		buttonPanel.setBackground(FirstPageGUI.green);
		buttonPanel.add(rateButton);
		buttonPanel.add(commentButton);
		buttonPanel.add(favoriteButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(dim.width, dim.height/10));
		bottomPanel.setBackground(FirstPageGUI.color);
		bottomPanel.add(backButton);
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		bottomPanel.add(forwardButton);
		add(bottomPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width, dim.height));
		mainPanel.add(album);
		//JPanel topPanel = new JPanel();
		album.setPreferredSize(new Dimension(dim.width/5, dim.height/5));
		mainPanel.add(artist);
		mainPanel.add(rating);
		mainPanel.add(backPageButton);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void createGUI()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(dim.width, dim.height/15));
		buttonPanel.setBackground(FirstPageGUI.green);
		buttonPanel.add(rateButton);
		buttonPanel.add(commentButton);
		buttonPanel.add(favoriteButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(dim.width, dim.height/10));
		bottomPanel.setBackground(FirstPageGUI.color);
		//bottomPanel.add(backButton);
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		//bottomPanel.add(forwardButton);
		add(bottomPanel, BorderLayout.SOUTH);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width, dim.height));
		mainPanel.add(album);
		//JPanel topPanel = new JPanel();
		album.setPreferredSize(new Dimension(dim.width/5, dim.height/5));
		mainPanel.add(artist);
		mainPanel.add(rating);
		mainPanel.add(backPageButton);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void setEventHandlers(){
		
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//musicObject.resumeSong();
				myThread.resume();
			}
		});
		
		pauseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//musicObject.pauseSong();
				myThread.suspend();
			}
		});
		
		forwardButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
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
				myThread = musicObject.playTheSong();
			}
			
		});
		
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
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
				myThread = musicObject.playTheSong();
			}
		});
		
		backPageButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				top.removePlayer();
				myThread.suspend();
			}
		});
		
	}
}