package us.congressionalappchallenge.scheduler.service.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookPage;
import us.congressionalappchallenge.scheduler.service.graphql.types.IntegrateFacebookInput;
import us.congressionalappchallenge.scheduler.service.service.IntegrationService;

import java.util.List;

@DgsComponent
public class IntegrationDataFetcher {
  private final IntegrationService integrationService;

  public IntegrationDataFetcher(IntegrationService integrationService) {
    this.integrationService = integrationService;
  }

  @DgsMutation
  public List<FacebookPage> integrateFacebook(
      @InputArgument IntegrateFacebookInput integrateFacebookInput) {
    return integrationService.integrateFacebook(integrateFacebookInput);
  }
}
