import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public abstract class TopGUI extends JPanel{
	protected ArrayList<JButton> buttons;
	protected ArrayList<MusicModel> songs;
	protected MusicPlayer myPlayer;
	protected int currentSong;
	protected Dimension dimPlayer;
	protected Dimension playerBigDim;
	protected LoggedInDriverGUI mainPage;
	public TopGUI()
	{
		setVisible(true);
		buttons = new ArrayList<JButton>();
		songs = new ArrayList<MusicModel>();
		currentSong = 0;
		createGUI();
		//add(new JButton("SONG"));
		//add(new JLabel("SONG"));
	}
	
	private void createGUI()
	{
		//JScrollPane jsp = new JScrollPane(this);
	}
	public abstract void fillButtons();
	public abstract MusicPlayer initPlayer();
	public abstract void addEventHandlers();
	
	public abstract void removePlayer();
	
	private class ButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//new MusicPlayer();
		}
	}
	
	public void stopSong()
	{
		myPlayer.stopThread();
	}
//	
//	public void startSong()
//	{
//		myPlayer.startThread();
//	}
}