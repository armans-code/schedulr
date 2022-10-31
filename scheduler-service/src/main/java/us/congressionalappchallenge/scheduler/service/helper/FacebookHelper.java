package us.congressionalappchallenge.scheduler.service.helper;

import com.facebook.ads.sdk.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.congressionalappchallenge.scheduler.service.config.FacebookProperties;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class FacebookHelper {

  private final FacebookProperties facebookProperties;

  public String getFacebookAuth() {
    return String.format(
        "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s",
        facebookProperties.getAppId(),
        facebookProperties.getFacebookCallbackUrl(),
        facebookProperties.getFacebookPermissions());
  }

  public String getFacebookAccessToken(String code, Boolean instagram) {
    try {
      APIContext context =
          new APIContext("", facebookProperties.getAppSecret(), facebookProperties.getAppId());
      APIRequest<APINode> request = new APIRequest<>(context, "oauth", "/access_token", "GET");
      request.setParam("client_id", facebookProperties.getAppId());
      request.setParam("client_secret", facebookProperties.getAppSecret());
      request.setParam("redirect_uri", instagram ? facebookProperties.getInstagramCallbackUrl() : facebookProperties.getFacebookCallbackUrl());
      request.setParam("code", code);
      APIResponse response = request.execute();
      return response.getRawResponseAsJsonObject().get("access_token").getAsString();
    } catch (APIException e) {
      throw new RuntimeException("Facebook API Error: " + e);
    }
  }

  public APINodeList<Page> getFacebookAccounts(String accessToken) {
    try {
      String userId = getFacebookUserId(accessToken);
      APIContext context =
          new APIContext(
              accessToken, facebookProperties.getAppSecret(), facebookProperties.getAppId());
      return new User.APIRequestGetAccounts(userId, context).execute();
    } catch (APIException e) {
      throw new RuntimeException("Facebook API Error: " + e);
    }
  }

  public String sendFacebookPost(
      String message,
      Optional<String> link,
      Optional<String> tags,
      Optional<String> place,
      Optional<String> scheduledPublishTime,
      FacebookAccountEntity facebookAccount) {
    try {
      APIContext context =
          new APIContext(
              facebookAccount.getAccessToken(),
              facebookProperties.getAppSecret(),
              facebookProperties.getAppId());
      Page.APIRequestCreateFeed apiRequest =
          new Page.APIRequestCreateFeed(facebookAccount.getFacebookId(), context);
      apiRequest.setMessage(message);
      link.ifPresent(apiRequest::setLink);
      tags.ifPresent(apiRequest::setTags);
      place.ifPresent(apiRequest::setPlace);
      scheduledPublishTime.ifPresent(
          t -> {
            apiRequest.setScheduledPublishTime(DateUtil.convert(t).toString());
            apiRequest.setPublished(false);
          });
      return apiRequest.execute().getId();
    } catch (APIException e) {
      throw new RuntimeException("Facebook Error: " + e);
    }
  }

  private String getFacebookUserId(String accessToken) throws APIException {
    APIContext context =
        new APIContext(
            accessToken, facebookProperties.getAppSecret(), facebookProperties.getAppId());
    APIResponse response = new APIRequest<>(context, "me", "/", "GET").execute();
    return response.getRawResponseAsJsonObject().get("id").getAsString();
  }
}
