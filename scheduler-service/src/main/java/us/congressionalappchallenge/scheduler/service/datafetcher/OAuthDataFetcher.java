package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeFacebookInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.AuthorizeTwitterInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookAccount;
import us.congressionalappchallenge.scheduler.service.graphql.types.TwitterAccount;
import us.congressionalappchallenge.scheduler.service.service.OAuthService;

import java.util.List;

@DgsComponent
public class OAuthDataFetcher {
  private static final Log log = LogFactory.getLog(OAuthDataFetcher.class);

  private final OAuthService oAuthService;

  public OAuthDataFetcher(OAuthService oAuthService) {
    this.oAuthService = oAuthService;
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public String facebookAuthUrl() {
    return oAuthService.facebookAuthUrl();
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public String twitterAuthUrl() {
    return oAuthService.twitterAuthUrl();
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #authorizeFacebookInput.getBusinessId() == authentication.principal.getUid()")
  public List<FacebookAccount> authorizeFacebook(
      @InputArgument AuthorizeFacebookInput authorizeFacebookInput) {
    return oAuthService.authorizeFacebook(authorizeFacebookInput.getBusinessId(), authorizeFacebookInput);
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #authorizeTwitterInput.getBusinessId() == authentication.principal.getUid()")
  public TwitterAccount authorizeTwitter(
      @InputArgument AuthorizeTwitterInput authorizeTwitterInput) {
    return oAuthService.authorizeTwitter(authorizeTwitterInput.getBusinessId(), authorizeTwitterInput);
  }
}
