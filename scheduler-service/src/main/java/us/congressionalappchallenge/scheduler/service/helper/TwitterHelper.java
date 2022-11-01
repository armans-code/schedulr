package us.congressionalappchallenge.scheduler.service.helper;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.pkce.PKCE;
import com.github.scribejava.core.pkce.PKCECodeChallengeMethod;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.auth.TwitterOAuth20Service;
import com.twitter.clientlib.model.Tweet;
import com.twitter.clientlib.model.TweetCreateRequest;
import com.twitter.clientlib.model.TweetCreateResponse;
import com.twitter.clientlib.model.TweetCreateResponseData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.congressionalappchallenge.scheduler.service.config.TwitterProperties;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;
import us.congressionalappchallenge.scheduler.service.util.PkceUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Component
@Slf4j
public class TwitterHelper {
  public final TwitterApi twitterApi;
  private final TwitterOAuth20Service twitterOauth;
  private final TwitterProperties twitterProperties;

  public Map<String, String> getTwitterAuth() {
    PKCE pkce = new PKCE();
    String codeVerifier = PkceUtil.generateCodeVerifier();
    String codeChallenge = PkceUtil.generateCodeChallenge(codeVerifier);
    pkce.setCodeChallenge(codeChallenge);
    pkce.setCodeVerifier(codeVerifier);
    pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.S256);
    return Map.of("url", twitterOauth.getAuthorizationUrl(pkce, "state"), "verifier", codeVerifier);
  }

  public OAuth2AccessToken getAccessToken(String code, String verifier) {
    try {
      PKCE pkce = new PKCE();
      pkce.setCodeChallenge(PkceUtil.generateCodeChallenge(verifier));
      pkce.setCodeVerifier(verifier);
      pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.S256);
      OAuth2AccessToken accessToken = twitterOauth.getAccessToken(pkce, code);
      twitterApi
          .getApiClient()
          .setTwitterCredentials(
              new TwitterCredentialsOAuth2(
                  twitterProperties.getClientId(),
                  twitterProperties.getClientSecret(),
                  accessToken.getAccessToken(),
                  accessToken.getRefreshToken()));
      return accessToken;
    } catch (IOException | InterruptedException | ExecutionException e) {
      throw new RuntimeException("Twitter Error: " + e);
    }
  }

  public TweetCreateResponseData sendTwitterTweet(
      String text, Optional<String> image, TwitterAccountEntity twitterAccount) {
    try {
      twitterApi
          .getApiClient()
          .setTwitterCredentials(
              new TwitterCredentialsOAuth2(
                  twitterProperties.getClientId(),
                  twitterProperties.getClientSecret(),
                  twitterAccount.getAccessToken(),
                  twitterAccount.getRefreshToken()));
      TweetCreateRequest tweetCreateRequest = new TweetCreateRequest();
      tweetCreateRequest.setText(text);
      // TODO maybe change this return type?
      return twitterApi.tweets().createTweet(tweetCreateRequest).execute().getData();
    } catch (ApiException e) {
      log.error("Post tweet error: ", e);
      throw new RuntimeException(e);
    }
  }
}
