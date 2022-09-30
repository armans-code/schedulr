package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.google.firebase.auth.FirebaseToken;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.helper.AuthHelper;
import us.congressionalappchallenge.scheduler.service.service.BusinessService;

@DgsComponent
public class BusinessDataFetcher {

  private final BusinessService businessService;
  private final AuthHelper authHelper;

  public BusinessDataFetcher(BusinessService businessService, AuthHelper authHelper) {
    this.businessService = businessService;
    this.authHelper = authHelper;
  }

  @DgsMutation
  public Business registerBusiness(@InputArgument RegisterBusinessInput registerBusinessInput) {
    return businessService.registerBusiness(registerBusinessInput);
  }

  @DgsQuery
  public Business business(@RequestHeader("Authorization") String token) {
    FirebaseToken user = authHelper.verifyUser(token.split("Bearer ")[1]);
    return businessService.getBusinessById(user.getUid());
  }
}
