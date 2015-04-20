import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
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
	private static final int BUFFER_SIZE = 2200;
	private SourceDataLine audioLine;
	
	
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
	
	//pausing song function
	public void pauseSong(){
		audioLine.stop();
	}
	
	//resume song
	public void resumeSong(){
		audioLine.start();
	}

	
	//constructor
	public MusicModel(){
		playButtonThatLeadsToMusicPlayer = new JButton("");
		
		playButtonThatLeadsToMusicPlayer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new MusicPlayer(songName);
			}
		});
	}
	
	class musicPlay extends Thread{
		//play song function
		public void playSong(){
			
	        try {
	        	//we have to change the songpath to a URL object
	        	URL url = new URL (songPath);
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
	            AudioFormat format = audioStream.getFormat();
	            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
	            audioLine = (SourceDataLine) AudioSystem.getLine(info);
	            audioLine.open(format);
	            audioLine.start();
	             
	            //song should begin
	            System.out.println("Playback started.");
	             
	            byte[] bytesBuffer = new byte[BUFFER_SIZE];
	            int bytesRead = -1;
	            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
	                audioLine.write(bytesBuffer, 0, bytesRead);
	            }
	            audioLine.drain();
	            audioLine.close();
	            audioStream.close();
	         
	            //song should end
	            System.out.println("Playback completed.");
	             
	        } catch (UnsupportedAudioFileException ex) {
	            System.out.println("The specified audio file is not supported.");
	            ex.printStackTrace();
	        } catch (LineUnavailableException ex) {
	            System.out.println("Audio line for playing back is unavailable.");
	            ex.printStackTrace();
	        } catch (IOException ex) {
	            System.out.println("Error playing the audio file.");
	            ex.printStackTrace();
	        }      
	    }
	
		
		//for multi-threading
		public void run(){
			this.playSong();
		}
	}

	
	
}
