package us.congressionalappchallenge.scheduler.service.helper;

import com.facebook.ads.sdk.*;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.congressionalappchallenge.scheduler.service.config.FacebookProperties;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class InstagramHelper {
  private final AccountFacade accountFacade;
  private final FacebookProperties facebookProperties;

  public String getInstagramAuthUrl() {
    return String.format(
        "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s",
        facebookProperties.getAppId(),
        facebookProperties.getInstagramCallbackUrl(),
        facebookProperties.getInstagramPermissions());
  }

  public APINodeList<InstagramUser> getInstagramAccounts(String accessToken, String pageId) {
    try {
      APIContext context =
          new APIContext(
              accessToken, facebookProperties.getAppSecret(), facebookProperties.getAppId());
      return new Page.APIRequestGetInstagramAccounts(pageId, context).execute();
    } catch (APIException e) {
      throw new RuntimeException("Instagram Error: " + e);
    }
  }

  public InstagramAccountEntity saveInstagramAccount(
      BusinessEntity business, FacebookAccountEntity facebookAccount, InstagramUser instagramUser) {
    Optional<InstagramAccountEntity> instagramAccount =
        accountFacade.getInstagramAccountRepository().findByInstagramId(instagramUser.getId());
    if (instagramAccount.isEmpty()) {
      InstagramAccountEntity accountEntity = new InstagramAccountEntity();
      accountEntity.setBusiness(business);
      accountEntity.setFacebookAccount(facebookAccount);
      accountEntity.setInstagramId(instagramUser.getId());
      return accountFacade.getInstagramAccountRepository().save(accountEntity);
    } else {
      return instagramAccount.get();
    }
  }

  public String sendInstagramPost(
      String caption, Optional<String> imageUrl, InstagramAccountEntity instagramAccount) {
    try {
      APIContext context =
          new APIContext(
              instagramAccount.getFacebookAccount().getAccessToken(),
              facebookProperties.getAppSecret(),
              facebookProperties.getAppId());
      APIRequest<IGMedia> mediaAPIRequest =
          new APIRequest<>(context, instagramAccount.getInstagramId(), "/media", "POST");
      mediaAPIRequest.setParam("caption", caption);
      if (imageUrl.isPresent()) mediaAPIRequest.setParam("image_url", imageUrl);
      mediaAPIRequest.setParam(
          "access_token", instagramAccount.getFacebookAccount().getAccessToken());
      JsonObject mediaCreated = mediaAPIRequest.execute().getRawResponseAsJsonObject();
      APIRequest<IGMedia> publishRequest =
          new APIRequest<>(context, instagramAccount.getInstagramId(), "/media_publish", "POST");
      publishRequest.setParam("creation_id", mediaCreated.get("id"));
      publishRequest.setParam(
          "access_token", instagramAccount.getFacebookAccount().getAccessToken());
      return publishRequest.execute().getRawResponseAsJsonObject().get("id").getAsString();
    } catch (APIException e) {
      throw new RuntimeException("Facebook Error: " + e);
    }
  }

  public List<InstagramAccountEntity> getAccountsByBusinessId(UUID businessId) {
    return accountFacade.getInstagramAccountRepository().findAllByBusinessId(businessId);
  }
}
