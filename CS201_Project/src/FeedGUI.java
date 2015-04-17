import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FeedGUI extends JPanel{
	private Dimension dim;
	public FeedGUI()
	{
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		//setResizable(false);
	}
}