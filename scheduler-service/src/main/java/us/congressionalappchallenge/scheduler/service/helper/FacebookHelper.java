package us.congressionalappchallenge.scheduler.service.helper;

import com.facebook.ads.sdk.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.congressionalappchallenge.scheduler.service.config.FacebookProperties;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateFacebookPostInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

import java.util.Objects;

@Component
@AllArgsConstructor
@Slf4j
public class FacebookHelper {

  private final FacebookProperties facebookProperties;

  public String getFacebookAuthUrl() {
    return String.format(
        "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&scope=%s",
        facebookProperties.getAppId(),
        facebookProperties.getCallbackUrl(),
        facebookProperties.getFacebookPermissions());
  }

  public String getFacebookAccessToken(String code) {
    try {
      APIContext context =
          new APIContext("", facebookProperties.getAppSecret(), facebookProperties.getAppId());
      APIRequest<APINode> request = new APIRequest<>(context, "oauth", "/access_token", "GET");
      request.setParam("client_id", facebookProperties.getAppId());
      request.setParam("client_secret", facebookProperties.getAppSecret());
      request.setParam("redirect_uri", facebookProperties.getCallbackUrl());
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
      CreateFacebookPostInput input, FacebookAccountEntity facebookAccount) {
    try {
      APIContext context =
          new APIContext(
              facebookAccount.getAccessToken(),
              facebookProperties.getAppSecret(),
              facebookProperties.getAppId());
      Page.APIRequestCreateFeed apiRequest =
          new Page.APIRequestCreateFeed(facebookAccount.getFacebookId(), context);
      apiRequest.setMessage(input.getMessage());
      if (!Objects.isNull(input.getLink())) apiRequest.setLink(input.getLink());
      if (!Objects.isNull(input.getPlace())) apiRequest.setPlace(input.getPlace());
      if (!Objects.isNull(input.getTags())) apiRequest.setTags(input.getTags());
      if (!Objects.isNull(input.getScheduledPublishTime())) {
        apiRequest.setScheduledPublishTime(
            DateUtil.convert(input.getScheduledPublishTime()).toString());
        apiRequest.setPublished(false);
      }
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
