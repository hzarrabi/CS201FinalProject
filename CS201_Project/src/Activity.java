import java.sql.Date;
import java.text.SimpleDateFormat;


public class Activity implements Comparable<Activity>{
	private int activity_id;
	private int user_id;
	private String description;
	private int rate;
	private int song_id;
	//private String timeStamp;
	private Date timestamp;
	
	public Activity(int activID, int userID, int songID, String desc, Date time) {
		activity_id = activID;
		user_id = userID;
		song_id = songID;
		String [] args = desc.split(" ");
		desc = args[0];
		if (args.length > 1)
		{
			rate = Integer.parseInt(args[1]);
		}
		description = desc;
		timestamp = time;
	}
	
	public int getActivityID() {
		return activity_id;
	}
	
	public int getRate()
	{
		return rate;
	}
	public int getUserID() {
		return user_id;
	}
	public int getSongID() {
		return song_id;
	}
	public String getDescription() {
		return description;
	}

	public Date getDateTime()
	{
		return timestamp;
	}
	@Override
	public int compareTo(Activity o) {
		return timestamp.compareTo(o.getDateTime());
	}
}
