package us.congressionalappchallenge.scheduler.service.helper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import us.congressionalappchallenge.scheduler.service.config.TwitterProperties;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;

import java.util.Optional;

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

    public String sendTwitterTweet(String message, Optional<String> image, TwitterAccountEntity twitterAccount) {
        try {
            twitter.setOAuthAccessToken(new AccessToken(twitterAccount.getToken(), twitterAccount.getTokenSecret()));
            StatusUpdate status = new StatusUpdate(message);
            // TODO maybe change this return type?
            return ((Long) twitter.updateStatus(status).getId()).toString();
        } catch (TwitterException e) {
            log.error("Post tweet error: ", e);
            throw new RuntimeException(e);
        }
    }

}
