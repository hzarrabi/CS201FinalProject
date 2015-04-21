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
import javax.swing.SwingConstants;


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
	private Thread myThread;
	
	private ArrayList<JButton> allButtons;
	private ArrayList<MusicModel> allSongs;
	private int currentSong;
	
	public MusicPlayer(Dimension d, ArrayList<JButton> buttons, ArrayList<MusicModel> songs, int currentSong)
	{
		this.currentSong = currentSong;
		this.allButtons = buttons;
		dim = d;
		this.allSongs = songs;
		musicObject = allSongs.get(currentSong);
		songName = musicObject.getSongName();
		initializeComponents();
		createUserGUI();
		setEventHandlers();
		setVisible(true);
		repaint();

	}
	
//	public void startThread()
//	{
//		if (myThread == null)
//			myThread = musicObject.playTheSong();
//		else
//			myThread.resume();
//	}
//	
	public void stopThread()
	{
		if (myThread != null)
			myThread.suspend();
	}	
	
	private void initializeComponents(){
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
		
		
		
		artist = new JLabel(musicObject.getSongName() + " "+musicObject.getArtistName());
		artist.setPreferredSize(new Dimension(dim.width-10, dim.height/15));
		rating = new JLabel("Rating and # of Listens");
		backButton = new JButton();
		
		playButton = new JButton();
		forwardButton = new JButton();
		pauseButton = new JButton();

	}
	
	private void createUserGUI()
	{
		setBackground(FirstPageGUI.white);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(dim.width, dim.height/20));
		buttonPanel.setBackground(FirstPageGUI.green);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(dim.width, dim.height/13));
		bottomPanel.setBackground(FirstPageGUI.color);
	
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setIcon(new ImageIcon("data/ReverseButton.png"));
		backButton.setPreferredSize(new Dimension(dim.width/5, dim.height/17));
		
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
		
		forwardButton.setOpaque(false);
		forwardButton.setContentAreaFilled(false);
		forwardButton.setBorderPainted(false);
		forwardButton.setIcon(new ImageIcon("data/forwardButton.png"));
		forwardButton.setPreferredSize(new Dimension(dim.width/5, dim.height/17));
		
		bottomPanel.add(backButton);
		bottomPanel.add(playButton);
		bottomPanel.add(pauseButton);
		bottomPanel.add(forwardButton);
		
		add(bottomPanel, BorderLayout.SOUTH);
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(FirstPageGUI.white);
		mainPanel.setPreferredSize(new Dimension(dim.width, dim.height));
		mainPanel.add(album);
		mainPanel.add(artist);
		artist.setBackground(FirstPageGUI.white);
		rating.setBackground(FirstPageGUI.white);
		artist.setForeground(FirstPageGUI.darkGrey);
		rating.setForeground(FirstPageGUI.darkGrey);
		artist.setFont(FirstPageGUI.font);
		rating.setFont(FirstPageGUI.font);
		rating.setHorizontalAlignment(SwingConstants.CENTER);
		artist.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(rating);
		add(mainPanel, BorderLayout.CENTER);
	}
	private void setEventHandlers(){
		
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//musicObject.resumeSong();
				if (myThread == null)
					myThread = musicObject.playTheSong();
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
				try
				{
					URL imageurl = new URL(musicObject.getAlbumPath());
					BufferedImage img = ImageIO.read(imageurl);
					ImageIcon icon = new ImageIcon(img);
					Image ResizedImage = icon.getImage().getScaledInstance(dim.width-10, dim.width-10, Image.SCALE_SMOOTH);
					album.setIcon(new ImageIcon(ResizedImage));
				} catch (IOException e1)
				{
					
				}
				artist.setText(musicObject.getArtistName() + " "+musicObject.getSongName());
				myThread = musicObject.playTheSong();
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
				try
				{
					URL imageurl = new URL(musicObject.getAlbumPath());
					BufferedImage img = ImageIO.read(imageurl);
					ImageIcon icon = new ImageIcon(img);
					Image ResizedImage = icon.getImage().getScaledInstance(dim.width-10, dim.width-10, Image.SCALE_SMOOTH);
					album.setIcon(new ImageIcon(ResizedImage));
				} catch (IOException e1)
				{
					
				}
				artist.setText(musicObject.getArtistName() + " "+musicObject.getSongName());
				myThread = musicObject.playTheSong();
			}
		});	
	}
}