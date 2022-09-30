package us.congressionalappchallenge.scheduler.service.service;

import facebook4j.Account;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4jads.auth.AccessToken;
import twitter4jads.auth.RequestToken;
import twitter4jads.internal.models4j.Twitter;
import twitter4jads.internal.models4j.TwitterException;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeFacebookInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeTwitterInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookAccount;
import us.congressionalappchallenge.scheduler.service.graphql.types.TwitterAccount;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookAccountRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.TwitterAccountRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OAuthService {
  private final Facebook facebook;
  private final Twitter twitter;
  private final FacebookAccountRepository facebookAccountRepository;
  private final TwitterAccountRepository twitterAccountRepository;
  private final BusinessRepository businessRepository;
  private final ModelMapper modelMapper;
  private final String callbackUrl;

  public OAuthService(
      Facebook facebook,
      Twitter twitter,
      FacebookAccountRepository facebookAccountRepository,
      TwitterAccountRepository twitterAccountRepository,
      BusinessRepository businessRepository,
      ModelMapper modelMapper,
      @Value("${oauth.callback-url}") String callbackUrl) {
    this.facebook = facebook;
    this.twitter = twitter;
    this.facebookAccountRepository = facebookAccountRepository;
    this.twitterAccountRepository = twitterAccountRepository;
    this.businessRepository = businessRepository;
    this.modelMapper = modelMapper;
    this.callbackUrl = callbackUrl;
  }

  public String facebookAuthUrl() {
    return facebook.getOAuthAuthorizationURL(callbackUrl);
  }

  public String twitterAuthUrl() {
    try {
      return twitter.getOAuthRequestToken(callbackUrl).getAuthorizationURL();
    } catch (TwitterException e) {
      throw new RuntimeException("Twitter Error: " + e);
    }
  }

  public List<FacebookAccount> authorizeFacebook(String userId, AuthorizeFacebookInput input) {
    try {
      BusinessEntity business = findBusinessById(UUID.fromString(userId));
      facebook.getOAuthAccessToken(input.getCode());
      return facebook.getAccounts().stream()
          .map(
              account ->
                  modelMapper.map(saveFacebookAccount(account, business), FacebookAccount.class))
          .collect(Collectors.toList());
    } catch (FacebookException e) {
      throw new RuntimeException("Facebook error: " + e);
    }
  }

  public TwitterAccount authorizeTwitter(String userId, AuthorizeTwitterInput input) {
    try {
      BusinessEntity business = findBusinessById(UUID.fromString(userId));
      AccessToken accessToken =
          twitter.getOAuthAccessToken(new RequestToken(input.getToken(), ""), input.getVerifier());
      return modelMapper.map(saveTwitterAccount(business, accessToken), TwitterAccount.class);
    } catch (TwitterException e) {
      throw new RuntimeException("Twitter error: " + e);
    }
  }

  private FacebookAccountEntity saveFacebookAccount(Account account, BusinessEntity organization) {
    FacebookAccountEntity accountEntity = new FacebookAccountEntity();
    accountEntity.setBusiness(organization);
    accountEntity.setName(account.getName());
    accountEntity.setFacebookId(account.getId());
    accountEntity.setAccessToken(account.getAccessToken());
    return facebookAccountRepository.save(accountEntity);
  }

  private TwitterAccountEntity saveTwitterAccount(BusinessEntity business, AccessToken accessToken)
      throws TwitterException {
    TwitterAccountEntity accountEntity = new TwitterAccountEntity();
    accountEntity.setBusiness(business);
    accountEntity.setTwitterId(((Long) twitter.getId()).toString());
    accountEntity.setName(twitter.getScreenName());
    accountEntity.setToken(accessToken.getToken());
    accountEntity.setTokenSecret(accessToken.getTokenSecret());
    return twitterAccountRepository.save(accountEntity);
  }

  private BusinessEntity findBusinessById(UUID id) {
    return businessRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Business not found for Id: " + id));
  }
}
