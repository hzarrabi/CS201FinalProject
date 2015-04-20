import java.awt.Dimension;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class TopRatedGUI extends TopGUI{

	public TopRatedGUI(Dimension d)
	{
		super();
		this.setPreferredSize(d);
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();
		addEventHandlers();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void fillButtons() {

			//int j = 0;
			for (Entry<String, MusicModel> entry : LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().entrySet()){
				String key = entry.getKey();
				buttons.add(LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(key).getPlayButtonThatLeadsToMusicPlayer());
			}
			for (int i = 0; i<((LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().size())); i++){
				this.add(buttons.get(i));	
				System.out.println(i);
			}
		}
	@Override
	public MusicPlayer initPlayer() {
		// TODO Auto-generated method stub
		myPlayer = new MusicPlayer(buttons, songs, currentSong);
		return myPlayer;
	}

}