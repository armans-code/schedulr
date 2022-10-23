package us.congressionalappchallenge.scheduler.service.service;

import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.auth.AccessToken;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeFacebookInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeInstagramInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeTwitterInput;
import us.congressionalappchallenge.scheduler.service.helper.FacebookHelper;
import us.congressionalappchallenge.scheduler.service.helper.InstagramHelper;
import us.congressionalappchallenge.scheduler.service.helper.TwitterHelper;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class IntegrationService {
  private final AccountFacade accountFacade;
  private final TwitterHelper twitterHelper;
  private final FacebookHelper facebookHelper;
  private final InstagramHelper instagramHelper;

  public String facebookAuthUrl() {
    return facebookHelper.getFacebookAuthUrl();
  }

  public String instagramAuthUrl() {
    return instagramHelper.getInstagramAuthUrl();
  }

  public String twitterAuthUrl() {
    return twitterHelper.getAuthUrl();
  }

  public List<FacebookAccountEntity> authorizeFacebook(
      String businessId, AuthorizeFacebookInput input) {
    BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(businessId));
    String accessToken = facebookHelper.getFacebookAccessToken(input.getCode());
    APINodeList<Page> pages = facebookHelper.getFacebookAccounts(accessToken);
    return pages.stream()
        .map(page -> accountFacade.saveFacebookAccount(page, business))
        .collect(Collectors.toList());
  }

  public List<InstagramAccountEntity> authorizeInstagram(
      String businessId, AuthorizeInstagramInput input) {
    BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(businessId));
    String accessToken = facebookHelper.getFacebookAccessToken(input.getCode());
    APINodeList<Page> pages = facebookHelper.getFacebookAccounts(accessToken);
    List<FacebookAccountEntity> storedPages =
        pages.stream()
            .map(page -> accountFacade.saveFacebookAccount(page, business))
            .collect(Collectors.toList());
    storedPages.forEach(
        page ->
            instagramHelper
                .getInstagramAccounts(page.getAccessToken(), page.getFacebookId())
                .forEach(account -> instagramHelper.saveInstagramAccount(business, page, account)));
    return instagramHelper.getAccountsByBusinessId(business.getId());
  }

  public TwitterAccountEntity authorizeTwitter(String businessId, AuthorizeTwitterInput input) {
    BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(businessId));
    AccessToken accessToken = twitterHelper.getAccessToken(input.getToken(), input.getVerifier());
    return accountFacade.saveTwitterAccount(
        business,
        accessToken,
        ((Long) accessToken.getUserId()).toString(),
        accessToken.getScreenName());
  }

  public List<FacebookAccountEntity> getFacebookAccounts(String businessId) {
    return accountFacade
        .getFacebookAccountRepository()
        .findAllByBusinessId(UUID.fromString(businessId));
  }

  public List<InstagramAccountEntity> getInstagramAccounts(String businessId) {
    return accountFacade
        .getInstagramAccountRepository()
        .findAllByBusinessId(UUID.fromString(businessId));
  }

  public List<TwitterAccountEntity> getTwitterAccounts(String businessId) {
    return accountFacade
        .getTwitterAccountRepository()
        .findAllByBusinessId(UUID.fromString(businessId));
  }
}
