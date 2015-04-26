
public class Activity {
	private int activity_id;
	private int user_id;
	private String description;
	private int song_id;
	private String timeStamp;
	
	public Activity(int activID, int userID, int songID, String desc, String time) {
		activity_id = activID;
		user_id = userID;
		song_id = songID;
		description = desc;
		timeStamp = time;
	}
	
	public int getActivityID() {
		return activity_id;
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
	public String getTimeStamp() {
		return timeStamp;
	}
}
