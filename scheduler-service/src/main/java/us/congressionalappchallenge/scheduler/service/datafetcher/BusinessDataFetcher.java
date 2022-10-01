package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.security.access.prepost.PreAuthorize;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.service.BusinessService;

@DgsComponent
public class BusinessDataFetcher {

  private final BusinessService businessService;

  public BusinessDataFetcher(BusinessService businessService) {
    this.businessService = businessService;
  }

  @DgsMutation
  public Business registerBusiness(@InputArgument RegisterBusinessInput registerBusinessInput) {
    return businessService.registerBusiness(registerBusinessInput);
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated() and #id == authentication.principal.getUid()")
  public Business business(@InputArgument String id) {
    return businessService.getBusinessById(id);
  }
}
