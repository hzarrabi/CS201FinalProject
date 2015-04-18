import javax.swing.JButton;


public class MusicModel {
	
	private int musicID;
	private String songName;
	private String artistName;
	private int ratingSum;
	private int numberOfRatings;
	private int numberOfPlayCounts;
	private String songPath;
	private String albumPath;
	private JButton playButtonThatLeadsToMusicPlayer;
	
	
	//MUSIC ID
	public int getMusicID(){
		return musicID;
	}
	public void setMusicID(int databaseMusicID){
		musicID = databaseMusicID;
	}
	
	//Song Name
	public String getSongName(){
		return songName;
	}
	public void setSongName(String databaseSongName){
		songName = databaseSongName;
	}
	
	//Artist Name
	public String getArtistName(){
		return artistName;
	}
	public void setArtistName(String databaseArtistName){
		artistName = databaseArtistName;
	}	
	
	//Rating Sum
	public int getRatingSum(){
		return ratingSum;
	}
	public void setRatingSum(int databseRatingSum){
		ratingSum = databseRatingSum;
	}
	
	//Number of ratings
	public int getNumberOfRatings(){
		return numberOfRatings;
	}
	public void setNumberOfRatings(int databseNumberOfRatings){
		numberOfRatings = databseNumberOfRatings;
	}
	
	//Number of Play counts
	public int getnumberOfPlayCounts(){
		return numberOfPlayCounts;
	}
	public void setnumberOfPlayCounts(int databseNumberOfPlayCounts){
		numberOfPlayCounts = databseNumberOfPlayCounts;
	}
	
	//Song Path
	public String getSongPath(){
		return songPath;
	}
	public void setSongPath(String databseSongPath){
		songPath = databseSongPath;
	}
	
	//Album path
	public String getAlbumPath(){
		return albumPath;
	}
	public void setAlbumPath(String databseAlbumPath){
		albumPath = databseAlbumPath;
	}
	
	//JButton
	public JButton getPlayButtonThatLeadsToMusicPlayer(){
		return playButtonThatLeadsToMusicPlayer;
	}
	public void setPlayButtonThatLeadsToMusicPlayer(String buttonName){
		playButtonThatLeadsToMusicPlayer.setText(buttonName);
	}
	
	//constructor
	public MusicModel(){
		playButtonThatLeadsToMusicPlayer = new JButton("");
	}
	
	
}
