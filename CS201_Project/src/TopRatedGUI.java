import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;

public class TopRatedGUI extends TopGUI{

	public TopRatedGUI(LoggedInDriverGUI main, Dimension d, Dimension playerDim, Dimension playerBigDim)
	{
		super();
		this.setPreferredSize(d);
		this.playerBigDim = playerBigDim;
		this.setBackground(FirstPageGUI.white);
		dimPlayer = playerDim;
		this.mainPage = main;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();
		addEventHandlers();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public TopRatedGUI( Dimension d, Dimension playerDim)
	{
		super();
		this.setPreferredSize(d);
		this.setBackground(FirstPageGUI.white);
		dimPlayer = playerDim;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		fillButtons();
		addEventHandlers();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void fillButtons() {

			//int j = 0;
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
	@Override
	public MusicPlayer initPlayer() {
		// TODO Auto-generated method stub
		myPlayer = new MusicPlayer(dimPlayer, buttons, songs, currentSong);
		return myPlayer;
	}
	
	class ActionListenerButtons implements ActionListener{

		private int current_song;
		public ActionListenerButtons(int i)
		{
			current_song = i;
		}
		public void actionPerformed(ActionEvent e) {
			IndpMusicPlayer thisMusicPlayer = new IndpMusicPlayer(TopRatedGUI.this, playerBigDim, buttons, songs, current_song);
			mainPage.changeRatedFrame(thisMusicPlayer);
		}
		
	}
	
	@Override
	public void addEventHandlers() {
		for (int j = 0; j < buttons.size(); j++)
		{
			JButton temp = buttons.get(j);
			temp.addActionListener(new ActionListenerButtons(j));
		}
		
	}

	@Override
	public void removePlayer() {
		mainPage.changeBackRatedFrame();
		
	}

}