package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.google.firebase.auth.FirebaseToken;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeFacebookInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeTwitterInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookAccount;
import us.congressionalappchallenge.scheduler.service.graphql.types.TwitterAccount;
import us.congressionalappchallenge.scheduler.service.helper.AuthHelper;
import us.congressionalappchallenge.scheduler.service.service.OAuthService;

import java.util.List;

@DgsComponent
public class OAuthDataFetcher {
  private final OAuthService oAuthService;
  private final AuthHelper authHelper;

  public OAuthDataFetcher(OAuthService oAuthService, AuthHelper authHelper) {
    this.oAuthService = oAuthService;
    this.authHelper = authHelper;
  }

  @DgsQuery
  public String facebookAuthUrl(@RequestHeader("Authorization") String token) {
    authHelper.verifyUser(token.split("Bearer ")[1]);
    return oAuthService.facebookAuthUrl();
  }

  @DgsQuery
  public String twitterAuthUrl(@RequestHeader("Authorization") String token) {
    authHelper.verifyUser(token.split("Bearer ")[1]);
    return oAuthService.twitterAuthUrl();
  }

  @DgsMutation
  public List<FacebookAccount> authorizeFacebook(
      @InputArgument AuthorizeFacebookInput authorizeFacebookInput,
      @RequestHeader("Authorization") String token) {
    FirebaseToken user = authHelper.verifyUser(token.split("Bearer ")[1]);
    return oAuthService.authorizeFacebook(user.getUid(), authorizeFacebookInput);
  }

  @DgsMutation
  public TwitterAccount authorizeTwitter(
      @InputArgument AuthorizeTwitterInput authorizeTwitterInput,
      @RequestHeader("Authorization") String token) {
    FirebaseToken user = authHelper.verifyUser(token.split("Bearer ")[1]);
    return oAuthService.authorizeTwitter(user.getUid(), authorizeTwitterInput);
  }
}
