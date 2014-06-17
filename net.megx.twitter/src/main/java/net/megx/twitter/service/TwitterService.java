package net.megx.twitter.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {

    private Twitter twitter = null;

    protected Log log = LogFactory.getLog(getClass());

    public TwitterService() {

        String propFilePath = "properties/twitter.properties";

        Properties props = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(
                propFilePath);
        try {
            props.load(in);
        } catch (IOException e) {
            log.error("Could not load twitter properties from Classpath at "
                    + propFilePath, e);
        } catch (NullPointerException e) {
            log.error("No file at " + propFilePath, e);
        }

        ConfigurationBuilder cb = new ConfigurationBuilder();

        try {
            int port = Integer.parseInt(props.getProperty("httpProxyPort"));
            cb.setHttpProxyPort(port);
        } catch (NumberFormatException e) {
            log.error("Proxy port not set: Could not parse proxy port number", e);
        }

        
        cb.setDebugEnabled(true)
                .setUseSSL(true)
                .setOAuthConsumerKey(props.getProperty("appKey"))
                .setOAuthConsumerSecret(props.getProperty("appSecret"))
                .setOAuthAccessToken(props.getProperty("accessToken"))
                .setOAuthAccessTokenSecret(
                        props.getProperty("accessTokenSecret"))
                .setRestBaseURL(
                        props.getProperty("restBaseURL",
                                "https://api.twitter.com/1.1/"))
                .setHttpProxyHost(props.getProperty("httpProxyHost", null));
                

        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
        try {
            twitter.getScreenName();
        } catch (TwitterException e) {
            String msg = "Could not estabslish twitter service: ";
            if (e.isCausedByNetworkIssue()) {
                log.error(msg + "Network issue.", e);
            }
            // TODO check for more precise stuff e.g. rate limit
        }
    }

    public void simpleTweet(String message) {
        try {
            this.twitter.updateStatus(message);
        } catch (TwitterException e) {
            log.error("Could not tweet: " + message, e);
        }
    }

    public void geoTweet(String message, Double latitude, Double longitude) {

        StatusUpdate tweet = new StatusUpdate(message);
        tweet.setDisplayCoordinates(true);
        tweet.setLocation(new GeoLocation(latitude, longitude));

        try {
            this.twitter.updateStatus(tweet);
        } catch (TwitterException e) {
            log.error("Could not tweet: " + message, e);
        }
    }

}
