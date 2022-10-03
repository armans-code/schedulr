package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import us.congressionalappchallenge.scheduler.service.graphql.types.*;
import us.congressionalappchallenge.scheduler.service.service.OAuthService;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
@AllArgsConstructor
@Slf4j
public class OAuthDataFetcher {

  private final OAuthService oAuthService;
  private final ModelMapper modelMapper;

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public String facebookAuthUrl() {
    return oAuthService.facebookAuthUrl();
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public String instagramAuthUrl() {
    return oAuthService.instagramAuthUrl();
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public String twitterAuthUrl() {
    return oAuthService.twitterAuthUrl();
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #authorizeFacebookInput.getBusinessId() == authentication.principal.getUid()")
  public List<FacebookAccount> authorizeFacebook(@InputArgument AuthorizeFacebookInput authorizeFacebookInput) {
    return oAuthService
        .authorizeFacebook(authorizeFacebookInput.getBusinessId(), authorizeFacebookInput)
        .stream()
        .map(account -> modelMapper.map(account, FacebookAccount.class))
        .collect(Collectors.toList());
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #authorizeInstagramInput.getBusinessId() == authentication.principal.getUid()")
  public List<InstagramAccount> authorizeInstagram(@InputArgument AuthorizeInstagramInput authorizeInstagramInput) {
    return oAuthService
        .authorizeInstagram(authorizeInstagramInput.getBusinessId(), authorizeInstagramInput)
        .stream()
        .map(account -> modelMapper.map(account, InstagramAccount.class))
        .collect(Collectors.toList());
  }

  @DgsMutation
  @PreAuthorize("isAuthenticated() and #authorizeTwitterInput.getBusinessId() == authentication.principal.getUid()")
  public TwitterAccount authorizeTwitter(@InputArgument AuthorizeTwitterInput authorizeTwitterInput) {
    return modelMapper.map(
        oAuthService.authorizeTwitter(authorizeTwitterInput.getBusinessId(), authorizeTwitterInput),
        TwitterAccount.class);
  }
}
