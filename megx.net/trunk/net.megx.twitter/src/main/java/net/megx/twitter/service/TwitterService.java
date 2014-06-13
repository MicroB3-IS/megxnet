package net.megx.twitter.service;

import java.io.InputStream;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {

	private Properties p = new Properties();
	private InputStream in = null;
	private ConfigurationBuilder cb = null;
	private TwitterFactory tf = null;
	private Twitter twitter = null;

	public TwitterService() {
		
		try {
			
			this.in = getClass().getClassLoader().getResourceAsStream(
					"properties/twitter.properties");
			this.p.load(in);
		
			this.cb = new ConfigurationBuilder();
			this.cb.setDebugEnabled(true).setUseSSL(true)
					.setOAuthConsumerKey(p.getProperty("consumerKey"))
					.setOAuthConsumerSecret(p.getProperty("consumerSecret"))
					.setOAuthAccessToken(p.getProperty("accessToken"))
					.setOAuthAccessTokenSecret(p.getProperty("accessTokenSecret"))
					.setRestBaseURL(p.getProperty("restBaseURL"));
			this.tf = new TwitterFactory(cb.build());
			this.twitter = tf.getInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateTwitterStatus(String tweetMessage) {
		try {
			this.twitter.updateStatus(tweetMessage);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}

}
