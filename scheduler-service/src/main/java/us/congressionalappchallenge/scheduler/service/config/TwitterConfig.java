package us.congressionalappchallenge.scheduler.service.config;

import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.auth.TwitterOAuth20Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import twitter4j.Twitter;
// import twitter4j.TwitterFactory;
// import twitter4j.conf.ConfigurationBuilder;

@Configuration
@AllArgsConstructor
@Slf4j
public class TwitterConfig {

  private final TwitterProperties twitterProperties;

  @Bean
  TwitterApi twitterApi() {
    if (twitterProperties.getClientId() == null
        || twitterProperties.getClientSecret() == null) {
      log.error(
          "Twitter properties not configured properly. Please check twitter.* properties settings in configuration file.");
      throw new RuntimeException(
          "Twitter properties not configured properly. Please check twitter.* properties settings in configuration file.");
    }
    TwitterCredentialsOAuth2 twitterCredentialsOAuth2 = new TwitterCredentialsOAuth2(
            twitterProperties.getClientId(), twitterProperties.getClientSecret(), "", "");
    return new TwitterApi(twitterCredentialsOAuth2);
  }

  @Bean
  TwitterOAuth20Service twitterOAuth20Service() {
    return new TwitterOAuth20Service(
        twitterProperties.getClientId(),
        twitterProperties.getClientSecret(),
        twitterProperties.getCallbackUrl(),
        twitterProperties.getScopes());
  }
}
