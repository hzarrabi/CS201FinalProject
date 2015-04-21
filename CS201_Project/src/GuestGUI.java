import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;


public class GuestGUI extends JFrame{
	
	private JTabbedPane jtp;
	private Dimension dim;
	private TopRatedGUI trg;
	private TopListenedGUI tlg;
	private JLabel title;
	private JButton logout;
	private JPanel titlePanel;
	JPanel bottomColor;
	public GuestGUI()
	{
		initializeComponents();
		createGUI();
		setEventHandlers();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		setResizable(false);
	}
	
	private void initializeComponents()
	{
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		logout = new JButton("Logout");
		jtp = new JTabbedPane();
		jtp.setBackground(FirstPageGUI.color);
		jtp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		trg = new TopRatedGUI(new Dimension(dim.width, 15*dim.height/20), new Dimension(dim.width/13, 15*dim.height/20));
		tlg = new TopListenedGUI(new Dimension(dim.width, 15*dim.height/20), new Dimension(dim.width/13, 15*dim.height/20));
		title = new JLabel("Top Tunes");
		//title.setPreferredSize(new Dimension(dim.width, dim.height/11));
		titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, 5*dim.height/20));
		bottomColor.setBackground(FirstPageGUI.color);
		//titlePanel.setPreferredSize(new Dimension(dim.width, dim.height));
	}
	
	private void createGUI()
	{
		jtp.addTab("Top Listened", tlg);
		jtp.addTab("Top Rated", trg);
		titlePanel.add(logout, BorderLayout.WEST);
		titlePanel.add(title, BorderLayout.CENTER);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		setLayout(new FlowLayout());
		add(titlePanel);
		add(jtp);
		//setBackground(FirstPageGUI.color);
		add(bottomColor);
	}
	
	private void setEventHandlers()
	{
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FirstPageGUI();
			}
		});
	}
}