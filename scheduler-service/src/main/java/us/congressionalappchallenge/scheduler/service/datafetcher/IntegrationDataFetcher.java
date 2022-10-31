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
import us.congressionalappchallenge.scheduler.service.service.IntegrationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DgsComponent
@AllArgsConstructor
@Slf4j
public class IntegrationDataFetcher {

  private final IntegrationService integrationService;
  private final ModelMapper modelMapper;

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public AuthUrl facebookAuthUrl() {
    return AuthUrl.newBuilder().url(integrationService.facebookAuth()).build();
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public AuthUrl instagramAuthUrl() {
    return AuthUrl.newBuilder().url(integrationService.instagramAuth()).build();
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated()")
  public AuthUrl twitterAuthUrl() {
    Map<String, String> auth = integrationService.twitterAuth();
    return AuthUrl.newBuilder().url(auth.get("url")).verifier(auth.get("verifier")).build();
  }

  @DgsMutation
  @PreAuthorize(
      "isAuthenticated() and #authorizeFacebookInput.getBusinessId() == authentication.principal.getUid()")
  public List<FacebookAccount> authorizeFacebook(
      @InputArgument AuthorizeFacebookInput authorizeFacebookInput) {
    return integrationService
        .authorizeFacebook(authorizeFacebookInput.getBusinessId(), authorizeFacebookInput)
        .stream()
        .map(account -> modelMapper.map(account, FacebookAccount.class))
        .collect(Collectors.toList());
  }

  @DgsMutation
  @PreAuthorize(
      "isAuthenticated() and #authorizeInstagramInput.getBusinessId() == authentication.principal.getUid()")
  public List<InstagramAccount> authorizeInstagram(
      @InputArgument AuthorizeInstagramInput authorizeInstagramInput) {
    return integrationService
        .authorizeInstagram(authorizeInstagramInput.getBusinessId(), authorizeInstagramInput)
        .stream()
        .map(account -> modelMapper.map(account, InstagramAccount.class))
        .collect(Collectors.toList());
  }

  @DgsMutation
  @PreAuthorize(
      "isAuthenticated() and #authorizeTwitterInput.getBusinessId() == authentication.principal.getUid()")
  public TwitterAccount authorizeTwitter(
      @InputArgument AuthorizeTwitterInput authorizeTwitterInput) {
    return modelMapper.map(
        integrationService.authorizeTwitter(
            authorizeTwitterInput.getBusinessId(), authorizeTwitterInput),
        TwitterAccount.class);
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated() and #businessId == authentication.principal.getUid()")
  public List<FacebookAccount> facebookAccounts(@InputArgument String businessId) {
    return integrationService.getFacebookAccounts(businessId).stream()
        .map(account -> modelMapper.map(account, FacebookAccount.class))
        .collect(Collectors.toList());
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated() and #businessId == authentication.principal.getUid()")
  public List<TwitterAccount> twitterAccounts(@InputArgument String businessId) {
    return integrationService.getTwitterAccounts(businessId).stream()
        .map(account -> modelMapper.map(account, TwitterAccount.class))
        .collect(Collectors.toList());
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated() and #businessId == authentication.principal.getUid()")
  public List<InstagramAccount> instagramAccounts(@InputArgument String businessId) {
    return integrationService.getInstagramAccounts(businessId).stream()
        .map(account -> modelMapper.map(account, InstagramAccount.class))
        .collect(Collectors.toList());
  }
}
