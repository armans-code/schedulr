package us.congressionalappchallenge.scheduler.service.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4jads.TwitterAds;
import twitter4jads.TwitterAdsFactory;
import twitter4jads.conf.ConfigurationBuilder;
import twitter4jads.internal.models4j.Twitter;

@Configuration
@AllArgsConstructor
@Slf4j
public class TwitterConfig {

  private final TwitterProperties twitterProperties;

  @Bean
  TwitterAdsFactory twitterAdsFactory() {
    if (twitterProperties.getConsumerKey() == null
        || twitterProperties.getConsumerSecret() == null) {
      log.error(
          "Twitter properties not configured properly. Please check twitter.* properties settings in configuration file.");
      throw new RuntimeException(
          "Twitter properties not configured properly. Please check twitter.* properties settings in configuration file.");
    }

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey(twitterProperties.getConsumerKey())
        .setOAuthConsumerSecret(twitterProperties.getConsumerSecret());
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
