import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.PreparedStatement;


public class SearchGUI extends JPanel {
	private Dimension dim;
	private JPanel jp1;
	private JTextField inputField;
	private JButton searchButton;
	private String searchText;
	private JScrollPane artistResults;
	private JScrollPane songResults;
	private JScrollPane userResults;
	private JLabel artist;
	private JLabel user;
	private JLabel song;
	private JPanel artistPanel;
	private JPanel songPanel;
	private JPanel userPanel;
	private int userID;
	private LoggedInDriverGUI mainPage;
	//private Connection conn;
	private String [] sql_queries = {"SELECT * from user_table WHERE username = ?", 
			"SELECT * from music_table WHERE song_name = ?",
			"SELECT * from music_table WHERE artist_name = ?"};
	
	private int userFollowID;

	public SearchGUI (Dimension d, int userID, Connection conn, LoggedInDriverGUI mainPage) {
		dim = d;
		this.userID = userID;
		this.setPreferredSize(dim);
		setBackground(FirstPageGUI.darkGrey);
		//this.conn = conn;
		this.mainPage = mainPage;
		inputField = new JTextField();
		searchButton = new JButton();
		inputField.setPreferredSize(new Dimension(4*dim.width/5, dim.height/15));
		inputField.setBackground(FirstPageGUI.grey);
		inputField.setForeground(FirstPageGUI.darkGrey);
		inputField.setBorder(new RoundedBorder());
		inputField.setFont(FirstPageGUI.smallFont);
		searchButton.setPreferredSize(new Dimension(dim.width/6, dim.height/15));
		searchButton.setBorder(new RoundedBorder());
		searchButton.setBackground(FirstPageGUI.darkGrey);
		searchButton.setIcon(new ImageIcon("data/searchSmall.png"));
		searchButton.setOpaque(true);
		//searchButton.setContentAreaFilled(false);
		searchButton.setBorderPainted(false);
		
		artistPanel = new JPanel();
		artistPanel.setPreferredSize(new Dimension(dim.width, dim.height/4));
		artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
		artistPanel.setBackground(FirstPageGUI.darkGrey);
		//fgScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		//artistPanel.setBackground(FirstPageGUI.white);
		userPanel = new JPanel();
		userPanel.setPreferredSize(new Dimension(dim.width, dim.height/4));
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		userPanel.setBackground(FirstPageGUI.white);
		userPanel.setBackground(FirstPageGUI.darkGrey);
		//fgScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		songPanel = new JPanel();
		songPanel.setPreferredSize(new Dimension(dim.width, dim.height/4));
		songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS));
		songPanel.setBackground(FirstPageGUI.white);
		songPanel.setBackground(FirstPageGUI.darkGrey);
		artistResults = new JScrollPane(artistPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		artistResults.setPreferredSize(new Dimension(dim.width, dim.height/4));
		artistResults.setBackground(FirstPageGUI.darkGrey);
		artistResults.setBorder(null);
		artistResults.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		songResults = new JScrollPane(songPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		songResults.setPreferredSize(new Dimension(dim.width, dim.height/4));
		songResults.setBackground(FirstPageGUI.darkGrey);
		songResults.setBorder(null);
		songResults.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		userResults = new JScrollPane(userPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		userResults.setPreferredSize(new Dimension(dim.width, dim.height/4));
		userResults.setBackground(FirstPageGUI.darkGrey);
		userResults.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
		userResults.setBorder(null);
		artist = new JLabel("Artist Results");
		artist.setBackground(FirstPageGUI.green);
		artist.setBorder(new RoundedBorder());
		artist.setOpaque(true);
		artist.setForeground(FirstPageGUI.white);
		artist.setPreferredSize(new Dimension(dim.width, dim.height/20));
		song = new JLabel("Song Results");
		song.setBorder(new RoundedBorder());
		song.setBackground(FirstPageGUI.green);
		song.setOpaque(true);
		song.setForeground(FirstPageGUI.white);
		song.setPreferredSize(new Dimension(dim.width, dim.height/20));
		user = new JLabel ("User Results");
		user.setPreferredSize(new Dimension(dim.width, dim.height/20));
		user.setBackground(FirstPageGUI.green);
		user.setForeground(FirstPageGUI.white);
		user.setOpaque(true);
		user.setBorder(new RoundedBorder());
		
		artist.setHorizontalTextPosition(SwingConstants.CENTER);
		song.setHorizontalTextPosition(SwingConstants.CENTER);
		user.setHorizontalTextPosition(SwingConstants.CENTER);
		artist.setFont(FirstPageGUI.font);
//		artist.setForeground(FirstPageGUI.darkGrey);
//		song.setForeground(FirstPageGUI.darkGrey);
//		user.setForeground(FirstPageGUI.darkGrey);
		song.setFont(FirstPageGUI.font);
		user.setFont(FirstPageGUI.font);
//		artist.setBackground(FirstPageGUI.white);
//		user.setBackground(FirstPageGUI.white);
//		song.setBackground(FirstPageGUI.white);
		JPanel one = new JPanel();
		one.setBackground(FirstPageGUI.darkGrey);
		one.setPreferredSize(new Dimension(dim.width, dim.height/13));
		JPanel two = new JPanel();
		two.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		two.setBackground(FirstPageGUI.darkGrey);
		JPanel three = new JPanel();
		three.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		three.setBackground(FirstPageGUI.darkGrey);
		JPanel four = new JPanel();
		four.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		four.setBackground(FirstPageGUI.darkGrey);
		
		one.add(searchButton);
		one.add(inputField);
		two.add(song);
		two.add(songResults);
		three.add(artist);
		three.add(artistResults);
		four.add(user);
		four.add(userResults);
		
		add(one);
		add(two);
		add(three);
		add(four);
		
		setEventHandlers();
		setVisible(true);
	}
	
	private void setEventHandlers() {
		searchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		
		inputField.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					search();	
                }       
			}
		});
	}
	
	private void search()
	{
		searchText = inputField.getText();
		artistPanel.removeAll();
		songPanel.removeAll();
		userPanel.removeAll();
		artistPanel.revalidate();
		artistPanel.repaint();
		songPanel.revalidate();
		songPanel.repaint();
		userPanel.revalidate();
		userPanel.repaint();
		try {
			Statement st = ConnectionClass.conn.createStatement();
			String queryCheck = "";
			boolean check_found = false;

			//check for users
			queryCheck = sql_queries[0];
			PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement(queryCheck);
			ps.setString(1, searchText);
			ResultSet rs = ps.executeQuery();
			if(rs.absolute(1))
			{	
				System.out.println("Username " + searchText +" exists!");
				check_found = true;
				String sql = "Select * from user_table Where username='" + searchText +"'";
				rs = st.executeQuery(sql);
				
				while (rs.next())
				{
					Integer userId2 = rs.getInt(1);
					String userFirstName = rs.getString(2);
					String userLastName = rs.getString(3);
					String profilePath = rs.getString(8);
					JButton name = new JButton(userFirstName + " "+userLastName);
					Statement st1 = ConnectionClass.conn.createStatement();
					String queryCheck1 = "SELECT user FROM friend_relationship WHERE user_being_followed = " + userId2 + " AND user = "+ LoggedInDriverGUI.userID;
					ResultSet rs1 = st1.executeQuery(queryCheck1);
					if (rs1.next())
					{
						name.addActionListener(new ActionListenerProfile(userId2, "friends"));
					}
					else
					{
						name.addActionListener(new ActionListenerProfile(userId2, "not friends"));
					}
					JLabel profileImage = new JLabel("");
					if (profilePath == null)
					{
						//System.out.println("here");
						ImageIcon icon = new ImageIcon("data/headphone_default.jpg");
						Image ResizedImage = icon.getImage().getScaledInstance(dim.width/10, dim.width/10, Image.SCALE_SMOOTH);
						profileImage.setIcon(new ImageIcon(ResizedImage));
					}
					else
					{
						try
						{
							URL imageurl = new URL(profilePath);
							BufferedImage img = ImageIO.read(imageurl);
							ImageIcon icon = new ImageIcon(img);
							Image ResizedImage = icon.getImage().getScaledInstance(dim.width/10, dim.width/10, Image.SCALE_SMOOTH);
							profileImage.setIcon(new ImageIcon(ResizedImage));
						} catch (IOException e1)
						{
							
						}
					}

					JPanel temp = new JPanel();
					temp.setPreferredSize(new Dimension(dim.width, dim.height/13));
					profileImage.setBorder(null);
					name.setBorder(null);
					name.setBackground(FirstPageGUI.darkGrey);
					name.setFont(FirstPageGUI.font);
					name.setForeground(FirstPageGUI.white);
					temp.add(profileImage);
					temp.add(name, BorderLayout.WEST);
					temp.setBackground(FirstPageGUI.darkGrey);
					userPanel.add(temp, BorderLayout.WEST);
					userPanel.revalidate();
					userPanel.repaint();
					//String profilePicPath
				}
				
				
				if (rs.next() && searchText.equals(rs.getString("username"))) {
					userFollowID = rs.getInt("iduser_table");
				}
				//addFollowButton();
			}
			else
			{
				JLabel nonFound = new JLabel("no users found");
				nonFound.setFont(FirstPageGUI.font);
				nonFound.setForeground(FirstPageGUI.white);
				userPanel.add(nonFound);
				userPanel.revalidate();
				userPanel.repaint();
			}
			//check for songs
			queryCheck = sql_queries[1];
			ps= (PreparedStatement) ConnectionClass.conn.prepareStatement(queryCheck);
			ps.setString(1, searchText);
			rs = ps.executeQuery();
			if(rs.absolute(1))
			{	
				MusicModel MusicObject = LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(rs.getString(2));
				
				JButton name = new JButton(MusicObject.getSongName() + " "+MusicObject.getArtistName());
				name.addActionListener(new ActionListenerNewPage(MusicObject, dim));
				JLabel profileImage = new JLabel("");
				try
				{
					URL imageurl = new URL(MusicObject.getAlbumPath());
					BufferedImage img = ImageIO.read(imageurl);
					ImageIcon icon = new ImageIcon(img);
					Image ResizedImage = icon.getImage().getScaledInstance(dim.height/10, dim.height/10, Image.SCALE_SMOOTH);
				//album.setIcon(new ImageIcon(ResizedImage));
					profileImage.setIcon(new ImageIcon(ResizedImage));
				}
				catch (Exception t)
				{
					
				}
				JPanel temp = new JPanel();
				temp.setPreferredSize(new Dimension(dim.width, dim.height/13));
				profileImage.setBorder(null);
				name.setBorder(null);
				name.setBackground(FirstPageGUI.darkGrey);
				name.setFont(FirstPageGUI.font);
				name.setForeground(FirstPageGUI.white);
				temp.add(profileImage);
				temp.add(name, BorderLayout.WEST);
				temp.setBackground(FirstPageGUI.darkGrey);
				songPanel.add(temp, BorderLayout.WEST);
				songPanel.revalidate();
				songPanel.repaint();
				System.out.println("found song");
				check_found = true;
			}
			else
			{
				JLabel nonFound = new JLabel("no songs found");
				nonFound.setFont(FirstPageGUI.font);
				nonFound.setForeground(FirstPageGUI.white);
				songPanel.add(nonFound);
				songPanel.revalidate();
				songPanel.repaint();
			}
			//check for artists
			queryCheck = sql_queries[2];
			ps= (PreparedStatement) ConnectionClass.conn.prepareStatement(queryCheck);
			ps.setString(1, searchText);
			rs = ps.executeQuery();
			if(rs.absolute(1))
			{	
				MusicModel MusicObject = LoggedInDriverGUI.sharedMusicLibrary.getMusicModelMap().get(rs.getString(2));
					
				JButton name = new JButton(MusicObject.getSongName() + " "+MusicObject.getArtistName());
				name.addActionListener(new ActionListenerNewPage(MusicObject, dim));
				JLabel profileImage = new JLabel("");
				try
				{
					URL imageurl = new URL(MusicObject.getAlbumPath());
					BufferedImage img = ImageIO.read(imageurl);
					ImageIcon icon = new ImageIcon(img);
					Image ResizedImage = icon.getImage().getScaledInstance(dim.height/10, dim.height/10, Image.SCALE_SMOOTH);
				//album.setIcon(new ImageIcon(ResizedImage));
					profileImage.setIcon(new ImageIcon(ResizedImage));
				}
				catch (Exception t)
				{
					
				}
				JPanel temp = new JPanel();
				temp.setPreferredSize(new Dimension(dim.width, dim.height/13));
				profileImage.setBorder(null);
				name.setBorder(null);
				name.setBackground(FirstPageGUI.darkGrey);
				name.setFont(FirstPageGUI.font);
				name.setForeground(FirstPageGUI.white);
				temp.add(profileImage, BorderLayout.WEST);
				temp.add(name);
				temp.setBackground(FirstPageGUI.darkGrey);
				artistPanel.add(temp, BorderLayout.WEST);
				artistPanel.revalidate();
				artistPanel.repaint();
				System.out.println("Artist " + searchText + " exists!");
				check_found = true;
			}
			else
			{
				JLabel notFound = new JLabel("no artist found");
				notFound.setFont(FirstPageGUI.font);
				notFound.setForeground(FirstPageGUI.white);
				artistPanel.add(notFound);
				artistPanel.revalidate();
				artistPanel.repaint();
			}
		
			if (!check_found)
			{
				System.out.println("Not found");
			}
			ps.close();
		
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		} 
	}
	class ActionListenerNewPage implements ActionListener{
		private MusicModel model;
		private Dimension dim;
		public ActionListenerNewPage(MusicModel model, Dimension dim)
		{
			this.model = model;
			this.dim = dim;
		}
		public void actionPerformed(ActionEvent e) {
				//IndpMusicPlayer player = new IndpMusicPlayer
			IndpMusicPlayer player = new IndpMusicPlayer(model, dim);
				mainPage.addCurrent(player);
			
		}
		
	}
	
	class ActionListenerProfile implements ActionListener{
		private int id;
		private String relation;
		public ActionListenerProfile(int id, String relationship)
		{
			this.id = id;
			this.relation = relationship;
		}
		public void actionPerformed(ActionEvent e) {
			ProfileGUI newProfile = new ProfileGUI(mainPage, dim, relation, id);
			newProfile.make();
				//IndpMusicPlayer player = new IndpMusicPlayer
			mainPage.addCurrent(newProfile);
			
		}
		
	}
}
