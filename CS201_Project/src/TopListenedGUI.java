import java.awt.Dimension;

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
		for (int i = 0; i<100; i++)
		{
			JButton b = new JButton("Song");
			buttons.add(b);
			//this.add(b);	
		}
		for (int i = 0; i<100; i++)
		{

			this.add(buttons.get(i));	
		}
	}

}