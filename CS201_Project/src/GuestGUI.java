
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class GuestGUI extends JFrame{
	private Dimension dim;
	private TopRatedGUI trg;
	private TopListenedGUI tlg;
	private JButton logout;
	private Thread currentSongThread;
	//private JLabel notifications;
	private JScrollPane trgScroll;
	private JScrollPane tlgScroll;
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JButton trgButton;
	private JButton tlgButton;
	private int currentJpanel;
	private Color myColor;
	JPanel bottomColor;
	private MusicPlayer musicPlayerTopRated;
	private MusicPlayer musicPlayerTopListened;
	private int currentPanelNum;
	
	public GuestGUI()
	{		
		super("Guest Screen");
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		dim = new Dimension(dim.width, dim.height-100);
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width/3, 35*dim.height/40));
		myColor = FirstPageGUI.color;
		mainPanel.setBackground(FirstPageGUI.color);
		initializeComponents();
		createGUI();
		setEventHandlers();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				//firstPage.setVisible(true);
				Object[] options = {"Yes",
                	"No"};
				int n = JOptionPane.showOptionDialog(GuestGUI.this,
			    "Would you like to logout?",
			    "",
			    JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
				if (n==0) {
					MusicPlayer.stopThread();
					new FirstPageGUI();
					GuestGUI.this.dispose();
				}
				if (n==1) {}
			}

		});
		setBounds(0,0,dim.width/3, dim.height);
		setResizable(false);
		setVisible(true);
	}
	
	private void initializeComponents()
	{
		currentJpanel = 0;
		trgButton = new JButton();
		tlgButton = new JButton();
		//mainPanel.setBackground(FirstPageGUI.color);
		trgButton.setIcon(new ImageIcon("data/star1.png"));
		tlgButton.setIcon(new ImageIcon("data/head1.png"));
		trgButton.setDisabledIcon(new ImageIcon("data/star1.png"));
		tlgButton.setDisabledIcon(new ImageIcon("data/head1.png"));
		trgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setBackground(myColor);
		tlgButton.setOpaque(false);
		tlgButton.setContentAreaFilled(false);
		tlgButton.setBorderPainted(false);
		
		trgButton.setOpaque(false);
		trgButton.setContentAreaFilled(false);
		trgButton.setBorderPainted(false);
		logout = new JButton("Logout");
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, dim.width/10, dim.height/100));

		buttonPanel.setBackground(myColor);
		buttonPanel.add(tlgButton);
		buttonPanel.add(trgButton);

		trg = new TopRatedGUI(new Dimension(6*dim.width/96, dim.height), new Dimension(11*dim.width/48, 38*dim.height/40), true);
		tlg = new TopListenedGUI(new Dimension(6*dim.width/96, dim.height), new Dimension(11*dim.width/48, 38*dim.height/40), true);

		trgScroll = new JScrollPane(trg);
		trgScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		tlgScroll = new JScrollPane(tlg);
		tlgScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		trgScroll.setPreferredSize(new Dimension(18*dim.width/192, 38*dim.height/40));
		tlgScroll.setPreferredSize(new Dimension(18*dim.width/192, 38*dim.height/40));
//		fgScroll.setPreferredSize(new Dimension(dim.width/3, 35*dim.height/40));
		trgScroll.setBorder(null);
		tlgScroll.setBorder(null);
		//mainPanel.add(trgScroll, BorderLayout.CENTER);
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(myColor);
		logout.setBorder(new RoundedBorder());
		logout.setBackground(FirstPageGUI.darkGrey);
		logout.setForeground(FirstPageGUI.white);
		logout.setFont(FirstPageGUI.smallFont);
		bottomColor.add(logout);
		logout.setOpaque(true);
		trg.fillButtons();
		tlg.fillButtons();
		musicPlayerTopRated = trg.initPlayer();
		musicPlayerTopListened = tlg.initPlayer();
		mainPanel.add(tlgScroll, BorderLayout.WEST);
		mainPanel.add(musicPlayerTopListened, BorderLayout.EAST);
	}
	
	private void createGUI()
	{
		add(buttonPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
		setBounds(0,0,dim.width/3, dim.height);
		setResizable(false);
		setVisible(true);
	}
	
	private void setEventHandlers()
	{
		
		trgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					removePanel();
					musicPlayerTopRated = trg.refresh();
					mainPanel.add(trgScroll, BorderLayout.WEST);
					mainPanel.add(musicPlayerTopRated, BorderLayout.EAST);
					currentJpanel = 1;
		            mainPanel.revalidate();
		            mainPanel.repaint();
				
			}
		});
		
		tlgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
					musicPlayerTopListened = tlg.refresh();
					mainPanel.add(tlgScroll, BorderLayout.WEST);
					mainPanel.add(musicPlayerTopListened, BorderLayout.EAST);
					currentJpanel = 0;
		            mainPanel.revalidate();
		            mainPanel.repaint();
			}
		});
	
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Yes",
	                    "No"};
				int n = JOptionPane.showOptionDialog(GuestGUI.this,
			    "Would you like to logout?",
			    "",
			    JOptionPane.OK_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
				if (n==0) {
					MusicPlayer.stopThread();
					dispose();
					new FirstPageGUI();	
				}
				if (n==1) {}
			}
		});
	}

	private void removePanel(){
			if (currentJpanel == 1) 
			{
				mainPanel.remove(trgScroll);
				mainPanel.remove(musicPlayerTopRated);
	            mainPanel.revalidate();
	            mainPanel.repaint();
				trg.stopSong();
			}
			else if (currentJpanel == 0)
			{
				mainPanel.remove(tlgScroll);
				mainPanel.remove(musicPlayerTopListened);
	            mainPanel.revalidate();
	            mainPanel.repaint();
				tlg.stopSong();
			}
	
	}
	
}