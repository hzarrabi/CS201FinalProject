import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class LoggedInDriverGUI extends JFrame{
	private Dimension dim;
	private TopRatedGUI trg;
	private TopListenedGUI tlg;
	private FeedGUI fg;
	private ProfileGUI mpg;
	private JButton logout;
	private JLabel notifications;
	private JScrollPane trgScroll;
	private JScrollPane tlgScroll;
	private JScrollPane mpgScroll;
	private JScrollPane fgScroll;
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JButton trgButton;
	private JButton tlgButton;
	private JButton mpgButton;
	private JButton feedButton;
	private JButton searchButton;
	private int currentJpanel;
	private Color myColor;
	
	JPanel bottomColor;
	
	public LoggedInDriverGUI()
	{
		super("Home Screen");
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
		currentJpanel = 0;
		fg = new FeedGUI();
		myColor = FirstPageGUI.color;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		trgButton = new JButton();
		tlgButton = new JButton();
		mpgButton = new JButton();
		feedButton = new JButton();
		searchButton = new JButton();
		trgButton.setIcon(new ImageIcon("data/star1.png"));
		tlgButton.setIcon(new ImageIcon("data/headphones1.png"));
		mpgButton.setIcon(new ImageIcon("data/profile.png"));
		feedButton.setIcon(new ImageIcon("data/home.png"));
		searchButton.setIcon(new ImageIcon("data/search.png"));
		trgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		mpgButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		searchButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		feedButton.setPreferredSize(new Dimension(dim.width/18, dim.height/16));
		tlgButton.setBackground(myColor);
		tlgButton.setOpaque(false);
		tlgButton.setContentAreaFilled(false);
		tlgButton.setBorderPainted(false);
		trgButton.setOpaque(false);
		trgButton.setContentAreaFilled(false);
		trgButton.setBorderPainted(false);
		mpgButton.setOpaque(false);
		mpgButton.setContentAreaFilled(false);
		mpgButton.setBorderPainted(false);
		searchButton.setOpaque(false);
		searchButton.setContentAreaFilled(false);
		searchButton.setBorderPainted(false);
		feedButton.setOpaque(false);
		feedButton.setContentAreaFilled(false);
		feedButton.setBorderPainted(false);
		
		logout = new JButton("Logout");
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(dim.width/3, dim.height/15));
		buttonPanel.setBackground(myColor);
		buttonPanel.add(feedButton);
		buttonPanel.add(mpgButton);
		buttonPanel.add(tlgButton);
		buttonPanel.add(trgButton);
		buttonPanel.add(searchButton);
		trg = new TopRatedGUI(new Dimension(dim.width/3, 15*dim.height/20));
		tlg = new TopListenedGUI(new Dimension(dim.width/3, 15*dim.height/20));
		mpg = new ProfileGUI(new Dimension(dim.width/3, 15*dim.height/20));
		trgScroll = new JScrollPane(trg);
		tlgScroll = new JScrollPane(tlg);
		fgScroll = new JScrollPane(fg);
		notifications = new JLabel("notifications");
		trgScroll.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		tlgScroll.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		fgScroll.setPreferredSize(new Dimension(dim.width/3, 15*dim.height/20));
		mainPanel.add(fgScroll, BorderLayout.CENTER);
		bottomColor = new JPanel();
		bottomColor.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		bottomColor.setBackground(myColor);
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
		
		trgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				mainPanel.add(trgScroll, BorderLayout.CENTER);
				currentJpanel = 3;
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
		
		tlgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				mainPanel.add(tlgScroll, BorderLayout.CENTER);
				currentJpanel = 4;
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
		mpgButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				mainPanel.add(mpg, BorderLayout.CENTER);
	            mainPanel.revalidate();
	            mainPanel.repaint();
				currentJpanel = 1;
			}
		});
		feedButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removePanel();
				currentJpanel = 0;
			    mainPanel.add(fgScroll, BorderLayout.CENTER);
	            mainPanel.revalidate();
	            mainPanel.repaint();
			}
		});
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FirstPageGUI();
			}
		});
	}
	
	private void removePanel()
	{
		switch(currentJpanel)
		{
			case 0: 
				mainPanel.remove(fgScroll);
				break;
			case 1:
				mainPanel.remove(mpg);
			case 2:
				//mainPanel.remove();
			case 3:
				mainPanel.remove(trgScroll);
			case 4:
				mainPanel.remove(tlgScroll);
		}
			
	}

}