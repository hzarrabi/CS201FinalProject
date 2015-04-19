import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.prism.Image;


public class MusicPlayer extends JFrame{
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
	private Boolean isGuest;
	private String songName;
	private MusicModel musicObject;
	
	public MusicPlayer(String songTitle, Boolean isGuest)
	{
		songName = songTitle;
		musicObject = LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(songName);
		this.isGuest = isGuest;
		initializeComponents();
		if (isGuest)
		{
			createGUI();
		}
		else
		{
			createUserGUI();
		}
		setEventHandlers();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		setResizable(false);
		//dim = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	private void initializeComponents(){
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		album = new JLabel("");
		
		try {
            URL imageurl = new URL(musicObject.getAlbumPath());
            BufferedImage img = ImageIO.read(imageurl);
            ImageIcon icon = new ImageIcon(img);
            //Image ResizedImage = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            album.setIcon(icon);
            album.setSize(475,475);
         } catch (IOException e) {
            e.printStackTrace();
         }
		
		
		
		artist = new JLabel(musicObject.getSongName());
		rating = new JLabel("Rating and # of Listens");
		backButton = new JButton("back");
		playButton = new JButton("Play");
		forwardButton = new JButton("forward");
		pauseButton = new JButton("Pause");

	}
	
	private void createUserGUI()
	{
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(dim.width/3, dim.height/10));
		bottomPanel.add(backButton);
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		bottomPanel.add(forwardButton);
		add(bottomPanel, BorderLayout.SOUTH);
		JPanel lyricsPanel = new JPanel();
		lyrics = new JTextArea();
		JScrollPane jsp = new JScrollPane(lyrics);
		jsp.setPreferredSize(new Dimension(dim.width/6, 1*dim.height/3));
		lyrics.setPreferredSize(new Dimension(dim.width/6, 1*dim.height/3));
		lyrics.setEditable(false);
		lyrics.setText("blah, blah, blah, blah \n blah, blah, blah, blah \n, blahblahblahblahblah \n, blahblahblahblahblah\n, blahblahblahblahblah \n, blahblahblahblahblah \n blahblahblahblahblah, \n");
		lyricsPanel.add(jsp);
		add(lyricsPanel, BorderLayout.CENTER);
		JPanel mainPanel = new JPanel();
		mainPanel.add(album);
		JPanel topPanel = new JPanel();
		album.setPreferredSize(new Dimension(dim.width/5, dim.height/5));
		mainPanel.add(artist);
		mainPanel.add(rating);
		add(mainPanel, BorderLayout.CENTER);
	}
	private void createGUI()
	{
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(dim.width/3, dim.height/10));
		bottomPanel.add(backButton);
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		bottomPanel.add(forwardButton);
		add(bottomPanel, BorderLayout.SOUTH);
		JPanel lyricsPanel = new JPanel();
		lyrics = new JTextArea();
		JScrollPane jsp = new JScrollPane(lyrics);
		jsp.setPreferredSize(new Dimension(dim.width/3, 1*dim.height/3));
		lyrics.setPreferredSize(new Dimension(dim.width/3, 1*dim.height/3));
		lyrics.setEditable(false);
		lyrics.setText("blah, blah, blah, blah \n blah, blah, blah, blah \n, blahblahblahblahblah \n, blahblahblahblahblah\n, blahblahblahblahblah \n, blahblahblahblahblah \n blahblahblahblahblah, \n");
		lyricsPanel.add(jsp);
		add(lyricsPanel, BorderLayout.CENTER);
		JPanel mainPanel = new JPanel();
		//mainPanel.add(album);
		JPanel topPanel = new JPanel();
		album.setPreferredSize(new Dimension(dim.width/5, dim.height/5));
		mainPanel.add(artist);
		mainPanel.add(rating);
		add(mainPanel, BorderLayout.CENTER);
	}
	
	private void setEventHandlers()
	{
		
	}
}