import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.mysql.jdbc.PreparedStatement;


public class TopListenedGUI extends TopGUI{
	private JButton currentButton;
	private Dimension d;
	private Boolean isGuest;
	private MusicLibrary library;
	public TopListenedGUI(LoggedInDriverGUI main, Dimension d, Dimension dimPlayer, Boolean isGuest)
	{
		super();
		this.setPreferredSize(d);
		this.mainPage = main;
		this.d = d;
		this.isGuest = isGuest;
		this.dimPlayer = dimPlayer;
		this.setBackground(FirstPageGUI.darkGrey);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//fillButtons();

		//addEventHandlers();
	}
	
	public TopListenedGUI( Dimension d, Dimension playerDim, Boolean isGuest)
	{
		super();
		try {
			library = new MusicLibrary();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setPreferredSize(d);
		this.d = d;
		this.isGuest = isGuest;
		this.setBackground(FirstPageGUI.darkGrey);
		dimPlayer = playerDim;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();

		//addEventHandlers();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	
	public void fillButtons() {
		
		ArrayList<MusicModel> topSongs;
		if (isGuest)
		{
			topSongs = library.getTopListenedSongs();
		}
		else
		{
			topSongs = LoggedInDriverGUI.sharedMusicLibrary.getTopListenedSongs();
		}
		songs = topSongs;
		for (int j = 0; j< topSongs.size(); j++){
			MusicModel entry = topSongs.get(j);
			JButton newButton = new JButton(entry.getSongName());
			newButton.setFont(FirstPageGUI.smallerFont);
			newButton.setPreferredSize(new Dimension(4*d.width/5, d.height/30));
			newButton.setBorder(new RoundedBorder());
			newButton.setBackground(FirstPageGUI.darkGrey);
			newButton.setForeground(FirstPageGUI.white);
			newButton.setOpaque(true);
			
			newButton.addActionListener(new ActionListenerButtons(j, entry, newButton));
			buttons.add(newButton);	
			//songs.add(entry);
		}
		for (int i = 0; i<topSongs.size(); i++){
			this.add(buttons.get(i));	
			//System.out.println(i);
		}
		JButton firstButton = buttons.get(0);
		currentButton = firstButton;
		currentButton.setForeground(FirstPageGUI.darkGrey);
		currentButton.setBackground(FirstPageGUI.white);
	}
	
	class ActionListenerButtons implements ActionListener{

		private int current_song;
		private MusicModel song;
		private JButton thisButton;
		public ActionListenerButtons(int i, MusicModel j, JButton thisButton)
		{
			song = j;
			current_song = i;
			this.thisButton = thisButton;
		}
		public void actionPerformed(ActionEvent e) {
			myPlayer.changeSong(song, current_song);
			currentButton.setForeground(FirstPageGUI.white);
			currentButton.setBackground(FirstPageGUI.darkGrey);
			currentButton = thisButton;
			currentButton.setForeground(FirstPageGUI.darkGrey);
			currentButton.setBackground(FirstPageGUI.white);
		}
		
	}
	
	public MusicPlayer initPlayer()
	{
		myPlayer = new MusicPlayer(dimPlayer, buttons,isGuest,  songs, currentSong);
		return myPlayer;
	}

	public MusicPlayer refresh()
	{
		this.removeAll();
		fillButtons();
		return initPlayer();
	}
//	@Override
//	public void addEventHandlers() {
//		for (int j = 0; j < buttons.size(); j++)
//		{
//			JButton temp = buttons.get(j);
//			temp.addActionListener(new ActionListenerButtons(j));
//		}
//		
//	}
}

