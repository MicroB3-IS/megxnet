package net.megx.twitter.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.megx.twitter.BaseTwitterService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterServiceImpl implements BaseTwitterService {

    private Twitter twitter = null;

    protected Log log = LogFactory.getLog(getClass());

    public TwitterServiceImpl() {

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

        String proxyPort = props.getProperty("httpProxyPort");
        if (proxyPort != null && !proxyPort.trim().isEmpty()) {
            try {

                int port = Integer.parseInt(proxyPort);
                cb.setHttpProxyPort(port);
            } catch (NumberFormatException e) {
                log.error(
                        "Proxy port not set: Could not parse proxy port number",
                        e);
            }
        }

        String proxyHost = props.getProperty("httpProxyHost");
        if (proxyHost != null && !proxyHost.trim().isEmpty()) {
            cb.setHttpProxyHost(props.getProperty("httpProxyHost", null));
        }

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(props.getProperty("appKey"))
                .setOAuthConsumerSecret(props.getProperty("appSecret"))
                .setOAuthAccessToken(props.getProperty("accessToken"))
                .setOAuthAccessTokenSecret(
                        props.getProperty("accessTokenSecret"))
                .setRestBaseURL(
                        props.getProperty("restBaseURL",
                                "https://api.twitter.com/1.1/"));

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
