import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;

public class TopRatedGUI extends TopGUI{

	public TopRatedGUI(LoggedInDriverGUI main, Dimension d, Dimension playerDim, Dimension playerBigDim)
	{
		super();
		this.setPreferredSize(d);
		this.playerBigDim = playerBigDim;
		this.setBackground(FirstPageGUI.darkGrey);
		dimPlayer = playerDim;
		this.mainPage = main;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();
		//addEventHandlers();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public TopRatedGUI( Dimension d, Dimension playerDim)
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
		ArrayList<MusicModel> topSongs = GuestGUI.sharedMusicLibrary.getTopRatedSongs();
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
			//System.out.println(i);
		}
	}
	
	public void fillButtons() {

		ArrayList<MusicModel> topSongs = LoggedInDriverGUI.sharedMusicLibrary.getTopRatedSongs();
		songs = topSongs;
		for (int j = 0; j< topSongs.size(); j++){
			MusicModel entry = topSongs.get(j);
			JButton newButton = new JButton(entry.getSongName());
			newButton.setFont(FirstPageGUI.smallFont);
			newButton.setBorder(new RoundedBorder());
			newButton.setBackground(FirstPageGUI.darkGrey);
			newButton.setForeground(FirstPageGUI.white);
			newButton.setOpaque(true);
			newButton.setEnabled(false);
			newButton.addActionListener(new ActionListenerButtons( entry, j));
			buttons.add(newButton);	
			//songs.add(entry);
		}
		for (int i = 0; i<topSongs.size(); i++){
			this.add(buttons.get(i));	
			//System.out.println(i);
		}
	}
	@Override
	public MusicPlayer initPlayer() {
		// TODO Auto-generated method stub
		myPlayer = new MusicPlayer(dimPlayer, buttons, songs, currentSong);
		return myPlayer;
	}
	
	class ActionListenerButtons implements ActionListener{

		private int current_song;
		private MusicModel song;
		public ActionListenerButtons(MusicModel j, int i)
		{
			song = j;
			current_song = i;
		}
		public void actionPerformed(ActionEvent e) {
			myPlayer.changeSong(song, current_song);
		}
		
	}

}