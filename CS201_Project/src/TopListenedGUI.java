import java.awt.Dimension;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class TopListenedGUI extends TopGUI{
	public TopListenedGUI(Dimension d, Dimension dimPlayer)
	{
		super();
		this.setPreferredSize(d);
		this.dimPlayer = dimPlayer;
		this.setBackground(FirstPageGUI.white);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();
		addEventHandlers();
	}
	
	public void fillButtons() {
		for (Entry<String, MusicModel> entry : LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().entrySet()){
			String key = entry.getKey();
			JButton newButton = new JButton(LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(key).getSongName());
			newButton.setFont(FirstPageGUI.smallFont);
			newButton.setBorder(new RoundedBorder());
			newButton.setBackground(FirstPageGUI.darkGrey);
			newButton.setForeground(FirstPageGUI.white);
			newButton.setOpaque(true);
			buttons.add(newButton);	
			songs.add(LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(key));
		}
		for (int i = 0; i<((LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().size())); i++){
			this.add(buttons.get(i));	
			System.out.println(i);
		}
	}
	
	public MusicPlayer initPlayer()
	{
		myPlayer = new MusicPlayer(dimPlayer, buttons, songs, currentSong);
		return myPlayer;
	}
}

