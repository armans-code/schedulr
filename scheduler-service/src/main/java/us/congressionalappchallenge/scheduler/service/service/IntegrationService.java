package us.congressionalappchallenge.scheduler.service.service;

import com.facebook.ads.sdk.APINodeList;
import com.facebook.ads.sdk.Page;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.model.Get2UsersMeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
import java.util.Map;
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

  public String facebookAuth() {
    return facebookHelper.getFacebookAuth();
  }

  public String instagramAuth() {
    return instagramHelper.getInstagramAuth();
  }

  public Map<String, String> twitterAuth() {
    return twitterHelper.getTwitterAuth();
  }

  public List<FacebookAccountEntity> authorizeFacebook(
      String businessId, AuthorizeFacebookInput input) {
    BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(businessId));
    String accessToken = facebookHelper.getFacebookAccessToken(input.getCode(), false);
    APINodeList<Page> pages = facebookHelper.getFacebookAccounts(accessToken);
    return pages.stream()
        .map(page -> accountFacade.saveFacebookAccount(page, business))
        .collect(Collectors.toList());
  }

  public List<InstagramAccountEntity> authorizeInstagram(
      String businessId, AuthorizeInstagramInput input) {
    BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(businessId));
    String accessToken = facebookHelper.getFacebookAccessToken(input.getCode(), true);
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
    try {
      BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(businessId));
      OAuth2AccessToken accessToken = twitterHelper.getAccessToken(input.getCode(), input.getVerifier());
      Get2UsersMeResponse user = twitterHelper.twitterApi.users().findMyUser().execute();
      return accountFacade.saveTwitterAccount(business, accessToken, user.getData().getId(), user.getData().getUsername());
    } catch (ApiException e) {
      throw new RuntimeException("Twitter Error: " + e);
    }
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
