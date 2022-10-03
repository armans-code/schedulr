package us.congressionalappchallenge.scheduler.service.helper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4jads.auth.AccessToken;
import twitter4jads.auth.RequestToken;
import twitter4jads.internal.models4j.Twitter;
import twitter4jads.internal.models4j.TwitterException;
import us.congressionalappchallenge.scheduler.service.config.TwitterProperties;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;

@Component
@AllArgsConstructor
@Slf4j
public class TwitterHelper {
  private final Twitter twitter;
  private final TwitterProperties twitterProperties;
  private final AccountFacade accountFacade;

  public String getAuthUrl() {
    try {
      return twitter.getOAuthRequestToken(twitterProperties.getCallbackUrl()).getAuthorizationURL();
    } catch (TwitterException e) {
      throw new RuntimeException("Twitter Error: " + e);
    }
  }

  public AccessToken getAccessToken(String token, String verifier) {
    try {
      return twitter.getOAuthAccessToken(new RequestToken(token, ""), verifier);
    } catch (TwitterException e) {
      throw new RuntimeException("Twitter Error: " + e);
    }
  }
}
