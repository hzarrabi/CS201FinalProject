import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class LoadingJPanel extends JPanel{
	
	private Dimension dim;
	public LoadingJPanel(Dimension d){
		dim = d;
		setPreferredSize(d);
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		// g.setColor(FirstPageGUI.transDarkGrey);
	       g.drawRect(0, 0, dim.width, dim.height);
	}

}
