import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	static MusicLibrary sharedMusicLibrary;
	private int currentJpanel;
	private Color myColor;
	private JPanel mainPanel;
	private JButton trgButton;
	private JButton tlgButton;
	private JComponent buttonPanel;
	private MusicPlayer musicPlayerTopRated;
	private MusicPlayer musicPlayerTopListened;
	private JScrollPane trgScroll;
	private JScrollPane tlgScroll;
	
	public GuestGUI(Dimension d)
	{
		dim = d;
//this.firstPage = firstPage;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				//firstPage.setVisible(true);
				new FirstPageGUI();
				
				GuestGUI.this.dispose();	
			}

		});
		try{
			sharedMusicLibrary = new MusicLibrary();
		}catch(Exception e){
			
		}
		initializeComponents();
		createGUI();
		setEventHandlers();
		setBounds(0,0,dim.width, dim.height);
		setVisible(true);
		setResizable(false);
	}
	
	private void initializeComponents()
	{
		currentJpanel = 0;
		myColor = FirstPageGUI.color;
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width, 35*dim.height/40));
		trgButton = new JButton();
		tlgButton = new JButton();
		trgButton.setIcon(new ImageIcon("data/star1.png"));
		tlgButton.setIcon(new ImageIcon("data/head1.png"));
		trgButton.setPreferredSize(new Dimension(dim.width/6, dim.height/16));
		tlgButton.setPreferredSize(new Dimension(dim.width/6, dim.height/16));
		tlgButton.setBackground(myColor);
		tlgButton.setOpaque(false);
		tlgButton.setContentAreaFilled(false);
		tlgButton.setBorderPainted(false);
		
		trgButton.setOpaque(false);
		trgButton.setContentAreaFilled(false);
		trgButton.setBorderPainted(false);
		
		logout = new JButton("Logout");
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, dim.width/4, dim.height/90));
		buttonPanel.setPreferredSize(new Dimension(dim.width, 3*dim.height/40));
		buttonPanel.setBackground(myColor);
		buttonPanel.add(tlgButton);
		
		buttonPanel.add(trgButton);

		trg = new TopRatedGUI(new Dimension(6*dim.width/32, 25*dim.height/40), new Dimension(11*dim.width/16, 25*dim.height/40));
		tlg = new TopListenedGUI(new Dimension(6*dim.width/32, 25*dim.height/40), new Dimension(11*dim.width/16, 25*dim.height/40));

		musicPlayerTopRated = trg.initPlayer();
		musicPlayerTopListened = tlg.initPlayer();

		trgScroll = new JScrollPane(trg);
		tlgScroll = new JScrollPane(tlg);
		trgScroll.setPreferredSize(new Dimension(18*dim.width/64, 25*dim.height/40));
		tlgScroll.setPreferredSize(new Dimension(18*dim.width/64, 25*dim.height/40));
		trgScroll.setBorder(null);
		tlgScroll.setBorder(null);
		mainPanel.add(tlgScroll, BorderLayout.WEST);
		mainPanel.add(musicPlayerTopListened, BorderLayout.CENTER);
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width, dim.height/20));
		bottomColor.setBackground(myColor);
		bottomColor.add(logout);
		mainPanel.setBackground(FirstPageGUI.white);
	}
	
	private void createGUI()
	{
		setLayout(new FlowLayout());
		add(buttonPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomColor, BorderLayout.SOUTH);
	}
	
	private void setEventHandlers()
	{
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FirstPageGUI();
			}
		});
		
		trgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
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
				mainPanel.add(tlgScroll, BorderLayout.WEST);
				mainPanel.add(musicPlayerTopListened, BorderLayout.EAST);
				currentJpanel = 0;
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
	}
	
	private void removePanel()
	{
			if (currentJpanel == 1) 
			{
				mainPanel.remove(trgScroll);
				mainPanel.remove(musicPlayerTopRated);
				trg.stopSong();
			}
			else if (currentJpanel == 0)
			{
				mainPanel.remove(tlgScroll);
				mainPanel.remove(musicPlayerTopListened);
				tlg.stopSong();
			}
	}
}