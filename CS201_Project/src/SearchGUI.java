import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
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
	private JButton add;
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
		//this.conn = conn;
		this.mainPage = mainPage;
		add = new JButton("Follow");
		inputField = new JTextField();
		searchButton = new JButton();
		inputField.setPreferredSize(new Dimension(4*dim.width/5, dim.height/15));
		inputField.setBackground(FirstPageGUI.grey);
		inputField.setForeground(FirstPageGUI.darkGrey);
		inputField.setBorder(new RoundedBorder());
		inputField.setFont(FirstPageGUI.smallFont);
		searchButton.setPreferredSize(new Dimension(dim.width/6, dim.height/15));
		searchButton.setBorder(new RoundedBorder());
		searchButton.setBackground(FirstPageGUI.green);
		searchButton.setIcon(new ImageIcon("data/searchSmall.png"));
		searchButton.setOpaque(true);
		//searchButton.setContentAreaFilled(false);
		searchButton.setBorderPainted(false);
		
		artistPanel = new JPanel();
		artistPanel.setPreferredSize(new Dimension(dim.width, dim.height/4));
		artistPanel.setLayout(new BoxLayout(artistPanel, BoxLayout.Y_AXIS));
		artistPanel.setBackground(FirstPageGUI.white);
		userPanel = new JPanel();
		userPanel.setPreferredSize(new Dimension(dim.width, dim.height/4));
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		userPanel.setBackground(FirstPageGUI.white);
		songPanel = new JPanel();
		songPanel.setPreferredSize(new Dimension(dim.width, dim.height/4));
		songPanel.setLayout(new BoxLayout(songPanel, BoxLayout.Y_AXIS));
		songPanel.setBackground(FirstPageGUI.white);
		artistResults = new JScrollPane(artistPanel);
		artistResults.setPreferredSize(new Dimension(dim.width, dim.height/4));
		songResults = new JScrollPane(songPanel);
		songResults.setPreferredSize(new Dimension(dim.width, dim.height/4));
		userResults = new JScrollPane(userPanel);
		userResults.setPreferredSize(new Dimension(dim.width, dim.height/4));
		artist = new JLabel("Artist Results");
		artist.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		song = new JLabel("Song Results");
		song.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		user = new JLabel ("User Results");
		user.setPreferredSize(new Dimension(dim.width/3, dim.height/20));
		
		artist.setHorizontalTextPosition(SwingConstants.CENTER);
		song.setHorizontalTextPosition(SwingConstants.CENTER);
		user.setHorizontalTextPosition(SwingConstants.CENTER);
		artist.setFont(FirstPageGUI.font);
		artist.setForeground(FirstPageGUI.darkGrey);
		song.setForeground(FirstPageGUI.darkGrey);
		user.setForeground(FirstPageGUI.darkGrey);
		song.setFont(FirstPageGUI.font);
		user.setFont(FirstPageGUI.font);
		artist.setBackground(FirstPageGUI.white);
		user.setBackground(FirstPageGUI.white);
		song.setBackground(FirstPageGUI.white);
		JPanel one = new JPanel();
		one.setBackground(FirstPageGUI.green);
		one.setPreferredSize(new Dimension(dim.width, dim.height/13));
		JPanel two = new JPanel();
		two.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		two.setBackground(FirstPageGUI.white);
		JPanel three = new JPanel();
		three.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		three.setBackground(FirstPageGUI.white);
		JPanel four = new JPanel();
		four.setPreferredSize(new Dimension(dim.width, 7*dim.height/24));
		four.setBackground(FirstPageGUI.white);
		
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
		
		
		//inputField.setEditable(true);
		//jp1 = new JPanel();
		//jp1.add(inputField, BorderLayout.NORTH);
		//jp1.add(searchButton, BorderLayout.SOUTH);
		//add(jp1, BorderLayout.CENTER);
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
		//follow user
		add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("INSERT INTO friend_relationship (user, user_being_followed)" + "VALUES (?, ?)");
					ps.setInt(1, userID);
					ps.setInt(2, userFollowID);
					ps.executeUpdate();
					ps.close();
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				}
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
					JButton name = new JButton(userFirstName + " "+userLastName);
					ProfileGUI newProfile;
					newProfile = new ProfileGUI(mainPage, dim, "not friends", userId2);
					name.addActionListener(new ActionListenerNewPage(newProfile));
					JLabel profileImage = new JLabel("");
					ImageIcon icon = new ImageIcon("data/MomAndMoose.jpg");
					Image ResizedImage = icon.getImage().getScaledInstance(dim.height/15, dim.height/15, Image.SCALE_SMOOTH);
					profileImage.setIcon(new ImageIcon(ResizedImage));
					JPanel temp = new JPanel();
					temp.setPreferredSize(new Dimension(dim.width, dim.height/13));
					temp.add(profileImage);
					temp.add(name);
					temp.setBackground(FirstPageGUI.white);
					userPanel.add(temp);
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
				nonFound.setForeground(FirstPageGUI.darkGrey);
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
				
//				//adding all of the values from the database to the object
//				MusicObject.setMusicID(rs.getInt(1));
//				MusicObject.setSongName(rs.getString(2));
//				MusicObject.setArtistName(rs.getString(3));
//				MusicObject.setRatingSum(rs.getInt(4));
//				MusicObject.setNumberOfRatings(rs.getInt(5));
//				MusicObject.setnumberOfPlayCounts(rs.getInt(6));
//				MusicObject.setSongPath(rs.getString(7));
//				MusicObject.setAlbumPath(rs.getString(8));
//				MusicObject.setPlayButtonThatLeadsToMusicPlayer(rs.getString(2));
				
				JButton name = new JButton(MusicObject.getSongName() + " "+MusicObject.getArtistName());
				IndpMusicPlayer player = new IndpMusicPlayer(MusicObject, dim);
				name.addActionListener(new ActionListenerNewPage(player));
				JLabel profileImage = new JLabel("");
				try
				{
					URL imageurl = new URL(MusicObject.getAlbumPath());
					BufferedImage img = ImageIO.read(imageurl);
					ImageIcon icon = new ImageIcon(img);
					Image ResizedImage = icon.getImage().getScaledInstance(dim.height/15, dim.height/15, Image.SCALE_SMOOTH);
				//album.setIcon(new ImageIcon(ResizedImage));
					profileImage.setIcon(new ImageIcon(ResizedImage));
				}
				catch (Exception t)
				{
					
				}
				JPanel temp = new JPanel();
				temp.setPreferredSize(new Dimension(dim.width, dim.height/13));
				temp.add(profileImage);
				temp.add(name);
				temp.setBackground(FirstPageGUI.white);
				songPanel.add(temp);
				songPanel.revalidate();
				songPanel.repaint();
				System.out.println("found song");
				check_found = true;
			}
			else
			{
				JLabel nonFound = new JLabel("no songs found");
				nonFound.setFont(FirstPageGUI.font);
				nonFound.setForeground(FirstPageGUI.darkGrey);
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
				
				//adding all of the values from the database to the object
//				MusicObject.setMusicID(rs.getInt(1));
//				MusicObject.setSongName(rs.getString(2));
//				MusicObject.setArtistName(rs.getString(3));
//				MusicObject.setRatingSum(rs.getInt(4));
//				MusicObject.setNumberOfRatings(rs.getInt(5));
//				MusicObject.setnumberOfPlayCounts(rs.getInt(6));
//				MusicObject.setSongPath(rs.getString(7));
//				MusicObject.setAlbumPath(rs.getString(8));
//				MusicObject.setPlayButtonThatLeadsToMusicPlayer(rs.getString(2));
//				
				JButton name = new JButton(MusicObject.getSongName() + " "+MusicObject.getArtistName());
				IndpMusicPlayer player = new IndpMusicPlayer(MusicObject, dim);
				name.addActionListener(new ActionListenerNewPage(player));
				JLabel profileImage = new JLabel("");
				try
				{
					URL imageurl = new URL(MusicObject.getAlbumPath());
					BufferedImage img = ImageIO.read(imageurl);
					ImageIcon icon = new ImageIcon(img);
					Image ResizedImage = icon.getImage().getScaledInstance(dim.height/15, dim.height/15, Image.SCALE_SMOOTH);
				//album.setIcon(new ImageIcon(ResizedImage));
					profileImage.setIcon(new ImageIcon(ResizedImage));
				}
				catch (Exception t)
				{
					
				}
				JPanel temp = new JPanel();
				temp.setPreferredSize(new Dimension(dim.width, dim.height/13));
				temp.add(profileImage);
				temp.add(name);
				temp.setBackground(FirstPageGUI.white);
				artistPanel.add(temp);
				artistPanel.revalidate();
				artistPanel.repaint();
				System.out.println("Artist " + searchText + " exists!");
				check_found = true;
			}
			else
			{
				JLabel notFound = new JLabel("no artist found");
				notFound.setFont(FirstPageGUI.font);
				notFound.setForeground(FirstPageGUI.darkGrey);
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
	private void addFollowButton() {
		add(add, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
	
	//ActionListener that I'm going to pass to the ProfileGUI for the back button to add
	//this will allow to back button to remove the profile page and go back to the search results
	
//	class ActionListenerProfile implements ActionListener{
//		private int id;
//		public ActionListenerProfile(int id)
//		{
//			this.id = id;
//		}
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			System.out.println("in actionlistener");
//			String sqlQuery = "SELECT COUNT(1) FROM friend_relationship WHERE EXISTS user = "+ LoggedInDriverGUI.userID+" AND user_being_followed = "+id+")";
//			ProfileGUI newProfile;
//			/*try{
//				System.out.println("in try");
//				PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sqlQuery);
//				ResultSet rs = ps.executeQuery();
//				//ResultSet rs = ps.executeQuery();
//				System.out.println("after rs");
//				
//				if (rs.getRow() == 0)
//				{*/
//					System.out.println("in if"); 
//					newProfile = new ProfileGUI(dim, "friends", id);
//					//mainPage.addGUI(newProfile);
//				/*}
//				else
//				{
//					System.out.println("in else");
//					newProfile = new ProfileGUI(dim, "not friends", userID, ConnectionClass.conn, new ActionListenerComplicated());
//					//mainPage.addGUI(newProfile);
//				}*/
//				mainPage.addCurrent(newProfile);
//				//ps.close();
//			//}
////			catch (Exception p){
////				
////			}
//			
//		}
//		
//	}
	
	class ActionListenerNewPage implements ActionListener{
		private JPanel myModel;
		public ActionListenerNewPage(JPanel player)
		{
			myModel = player;
		}
		public void actionPerformed(ActionEvent e) {
				//IndpMusicPlayer player = new IndpMusicPlayer
				mainPage.addCurrent(myModel);
			
		}
		
	}
}
