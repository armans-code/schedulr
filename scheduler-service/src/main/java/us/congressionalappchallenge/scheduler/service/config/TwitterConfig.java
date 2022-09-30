package us.congressionalappchallenge.scheduler.service.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4jads.TwitterAds;
import twitter4jads.TwitterAdsFactory;
import twitter4jads.conf.ConfigurationBuilder;
import twitter4jads.internal.models4j.Twitter;

@Configuration
public class TwitterConfig {
  private static final Log log = LogFactory.getLog(TwitterConfig.class);

  @Value("${twitter.consumer-key}")
  private String consumerKey;

  @Value("${twitter.consumer-secret}")
  private String consumerSecret;

  @Bean
  TwitterAdsFactory twitterAdsFactory() {
    if (consumerKey == null || consumerSecret == null) {
      log.error(
          "Twitter properties not configured properly. Please check twitter.* properties settings in configuration file.");
      throw new RuntimeException(
          "Twitter properties not configured properly. Please check twitter.* properties settings in configuration file.");
    }

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret);
    return new TwitterAdsFactory(cb.build());
  }

  @Bean
  Twitter twitter() {
    return twitterAdsFactory().getInstance();
  }

  @Bean
  TwitterAds twitterAds() {
    return twitterAdsFactory().getAdsInstance();
  }
}
