import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ProfileGUI.ActionListenerProfile;

public class FeedGUI extends JPanel{
	private Dimension dim;
	private Vector<Activity> activities;;
	public FeedGUI()
	{
		
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		activities = new Vector<Activity>();
		setBounds(0,0,dim.width/3, dim.height);
		setVisible(true);
		makeActivities();
		//setResizable(false);
	}
	
	public void makeGUI()
	{
		for (int i = 0; i < activities.size(); i++)
		{
			
		}
	}
	public void makeActivities(){
		
		try
		{
			//ConnectionClass.conn = DriverManager.getConnection("jdbc:mysql://104.236.176.180/cs201", "cs201", "manishhostage");
			
			Statement st = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck = "SELECT user_being_followed FROM friend_relationship WHERE user = " + Integer.toString(LoggedInDriverGUI.userID);
			ResultSet rs = st.executeQuery(queryCheck);
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next())
			{
				Statement st2 = ConnectionClass.conn.createStatement();
				//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
				String queryCheck2 = "SELECT activity_id FROM activity_feed WHERE user_id = " + rs.getInt(1);
				ResultSet rs2 = st.executeQuery(queryCheck);
				int columns2 = rs.getMetaData().getColumnCount();
				while (rs2.next())
				{
					Integer activity_id = rs.getInt(1);
					Integer user_id = rs.getInt(2);
					Integer song_id = rs.getInt(4);
					String description = rs.getString(3);
					String timeStamp = rs.getString(5);
					Activity newActivity = new Activity(activity_id, user_id, song_id, description, timeStamp);
					activities.add(newActivity);
					
				}
				st2.close();
			}
			st.close();
			
			Statement st3 = ConnectionClass.conn.createStatement();
			//PreparedStatement ps = (PreparedStatement) ConnectionClass.conn.prepareStatement("SELECT song_id FROM favorite_songs WHERE user_id = " + Integer.toString(LoggedInDriverGUI.userID));
			String queryCheck2 = "SELECT activity_id FROM activity_feed WHERE user_id = " + LoggedInDriverGUI.userID;
			ResultSet rs2 = st.executeQuery(queryCheck);
			ResultSet rs3 = st.executeQuery(queryCheck);
			int columns3 = rs.getMetaData().getColumnCount();
			while (rs3.next())
			{
				Integer activity_id = rs.getInt(1);
				Integer user_id = rs.getInt(2);
				Integer song_id = rs.getInt(4);
				String description = rs.getString(3);
				String timeStamp = rs.getString(5);
				Activity newActivity = new Activity(activity_id, user_id, song_id, description, timeStamp);
				activities.add(newActivity);
			}
			
		}
		catch (Exception e) {}
	}
}