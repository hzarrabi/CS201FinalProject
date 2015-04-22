import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.PreparedStatement;


public class SearchGUI extends JPanel {
	private Dimension dim;
	private JPanel jp1;
	private JTextField inputField;
	private JButton searchButton;
	private String searchText;
	private JButton add;
	private int userID;
	private Connection conn;
	private String [] sql_queries = {"SELECT * from user_table WHERE username = ?", 
			"SELECT * from music_table WHERE song_name = ?",
			"SELECT * from music_table WHERE artist_name = ?"};
	
	private int userFollowID;

	public SearchGUI (Dimension d, int userID, Connection conn) {
		dim = d;
		this.userID = userID;
		this.setPreferredSize(dim);
		this.conn = conn;
		
		add = new JButton("Follow");
		inputField = new JTextField();
		inputField.setPreferredSize(new Dimension(dim.width/3, dim.height/2));
		inputField.setEditable(true);
		searchButton = new JButton("Search");
		jp1 = new JPanel();
		jp1.add(inputField, BorderLayout.NORTH);
		jp1.add(searchButton, BorderLayout.SOUTH);
		add(jp1, BorderLayout.CENTER);
		setEventHandlers();
		setVisible(true);
	}
	
	private void setEventHandlers() {
		searchButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				searchText = inputField.getText();
				
				try {
					Statement st = conn.createStatement();
					String queryCheck = "";
					boolean check_found = false;

					//check for users
					queryCheck = sql_queries[0];
					PreparedStatement ps = (PreparedStatement) conn.prepareStatement(queryCheck);
					ps.setString(1, searchText);
					ResultSet rs = ps.executeQuery();
					if(rs.absolute(1))
					{	
						System.out.println("Username " + searchText +" exists!");
						check_found = true;
						String sql = "Select * from user_table Where username='" + searchText +"'";
						rs = st.executeQuery(sql);
						if (rs.next() && searchText.equals(rs.getString("username"))) {
							userFollowID = rs.getInt("iduser_table");
						}
						addFollowButton();
					}
					//check for songs
					queryCheck = sql_queries[1];
					ps= (PreparedStatement) conn.prepareStatement(queryCheck);
					ps.setString(1, searchText);
					rs = ps.executeQuery();
					if(rs.absolute(1))
					{	
						System.out.println("Song " + searchText + " exists!");
						check_found = true;
					}
					//check for artists
					queryCheck = sql_queries[2];
					ps= (PreparedStatement) conn.prepareStatement(queryCheck);
					ps.setString(1, searchText);
					rs = ps.executeQuery();
					if(rs.absolute(1))
					{	
						System.out.println("Artist " + searchText + " exists!");
						check_found = true;
					}
				
					if (!check_found)
					{
						System.out.println("Not found");
					}
				
				} catch (SQLException e1)
				{
					e1.printStackTrace();
				} 	}

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
	}
	private void addFollowButton() {
		add(add, BorderLayout.SOUTH);
		revalidate();
		repaint();
	}
}
