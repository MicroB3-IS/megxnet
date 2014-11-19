package net.megx.twitter;

public interface BaseTwitterService {
	
	 public void simpleTweet(String message);
	 public void geoTweet(String message, Double latitude, Double longitude);

}
