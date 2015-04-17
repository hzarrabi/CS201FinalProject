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
	public TopGUI()
	{
		setVisible(true);
		buttons = new ArrayList<JButton>();
		createGUI();
		//add(new JButton("SONG"));
		//add(new JLabel("SONG"));
	}
	
	private void createGUI()
	{
		//JScrollPane jsp = new JScrollPane(this);
	}
	public abstract void fillButtons();
	
	protected void addEventHandlers()
	{
		for (int i = 0; i<buttons.size(); i++)
		{
			buttons.get(i).addActionListener(new ButtonActionListener());
		}
	}
	
	private class ButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			new MusicPlayer();
		}
	}
}