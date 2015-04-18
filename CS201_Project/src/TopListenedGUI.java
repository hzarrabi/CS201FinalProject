import java.awt.Dimension;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class TopListenedGUI extends TopGUI{
	public TopListenedGUI(Dimension d)
	{
		super();
		this.setPreferredSize(d);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();
		addEventHandlers();
	}
	
	public void fillButtons() {

		for (Entry<String, MusicModel> entry : LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().entrySet()){
			String key = entry.getKey();
			buttons.add(LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(key).getPlayButtonThatLeadsToMusicPlayer());	
		}
		for (int i = 0; i<((LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().size())); i++){
			this.add(buttons.get(i));	
			System.out.println(i);
		}
	}

}

