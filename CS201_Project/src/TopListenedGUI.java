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
	public TopListenedGUI(LoggedInDriverGUI main, Dimension d, Dimension dimPlayer, Dimension dimBigPlayer)
	{
		super();
		this.setPreferredSize(d);
		this.mainPage = main;
		this.dimPlayer = dimPlayer;
		this.setBackground(FirstPageGUI.darkGrey);
		this.playerBigDim = dimBigPlayer;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();
		//addEventHandlers();
	}
	
	public TopListenedGUI( Dimension d, Dimension playerDim)
	{
		super();
		this.setPreferredSize(d);
		this.setBackground(FirstPageGUI.darkGrey);
		dimPlayer = playerDim;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtonsGuest();
		//addEventHandlers();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void fillButtonsGuest()
	{
		ArrayList<MusicModel> topSongs = GuestGUI.sharedMusicLibrary.getTopListenedSongs();
		songs = topSongs;
		for (int j = 0; j< topSongs.size(); j++){
			MusicModel entry = topSongs.get(j);
			JButton newButton = new JButton(entry.getSongName());
			newButton.setFont(FirstPageGUI.smallFont);
			newButton.setBorder(new RoundedBorder());
			newButton.setBackground(FirstPageGUI.darkGrey);
			newButton.setForeground(FirstPageGUI.white);
			newButton.setOpaque(true);
			buttons.add(newButton);	
			//songs.add(entry);
		}
		for (int i = 0; i<topSongs.size(); i++){
			this.add(buttons.get(i));	
			System.out.println(i);
		}
	}
	
	public void fillButtons() {
		
		ArrayList<MusicModel> topSongs = LoggedInDriverGUI.sharedMusicLibrary.getTopListenedSongs();
		songs = topSongs;
		for (int j = 0; j< topSongs.size(); j++){
			MusicModel entry = topSongs.get(j);
			JButton newButton = new JButton(entry.getSongName());
			newButton.setFont(FirstPageGUI.smallFont);
			newButton.setBorder(new RoundedBorder());
			newButton.setBackground(FirstPageGUI.darkGrey);
			newButton.setForeground(FirstPageGUI.white);
			newButton.setOpaque(true);
			newButton.addActionListener(new ActionListenerButtons(j, entry));
			buttons.add(newButton);	
			//songs.add(entry);
		}
		for (int i = 0; i<topSongs.size(); i++){
			this.add(buttons.get(i));	
			System.out.println(i);
		}
	}
	
	class ActionListenerButtons implements ActionListener{

		private int current_song;
		private MusicModel song;
		public ActionListenerButtons(int i, MusicModel j)
		{
			song = j;
			current_song = i;
		}
		public void actionPerformed(ActionEvent e) {
			myPlayer.changeSong(song, current_song);
		}
		
	}
	
	public MusicPlayer initPlayer()
	{
		myPlayer = new MusicPlayer(dimPlayer, buttons, songs, currentSong);
		return myPlayer;
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

	@Override
	public void removePlayer() {
		mainPage.changeBackListenedFrame();
		
	}
}

