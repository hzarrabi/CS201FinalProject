
public class MusicModel {
	
	private int musicID;
	private String songName;
	private String artistName;
	private int ratingSum;
	private int numberOfRatings;
	private int numberOfPlayCounts;
	private String songPath;
	private String albumPath;
	
	//MUSIC ID
	private int getMusicID(){
		return musicID;
	}
	private void setMusicID(int databaseMusicID){
		musicID = databaseMusicID;
	}
	
	//Song Name
	private String getSongName(){
		return songName;
	}
	private void setSongName(String databaseSongName){
		songName = databaseSongName;
	}
	
	//Artist Name
	private String getArtistName(){
		return artistName;
	}
	private void setArtistName(String databaseArtistName){
		artistName = databaseArtistName;
	}	
	
	//Rating Sum
	private int getRatingSum(){
		return ratingSum;
	}
	private void setRatingSum(int databseRatingSum){
		ratingSum = databseRatingSum;
	}
	
	//
	private int getNumberOfRatings(){
		return numberOfRatings;
	}
	private int getnumberOfPlayCounts(){
		return numberOfPlayCounts;
	}
	private String getSongPath(){
		return songPath;
	}
	private String getAlbumPath(){
		return albumPath;
	}
	
	

}
