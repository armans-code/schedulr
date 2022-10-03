package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.service.BusinessService;

@DgsComponent
@AllArgsConstructor
@Slf4j
public class BusinessDataFetcher {

  private final BusinessService businessService;
  private final ModelMapper modelMapper;

  @DgsMutation
  public Business registerBusiness(@InputArgument RegisterBusinessInput registerBusinessInput) {
    return modelMapper.map(businessService.registerBusiness(registerBusinessInput), Business.class);
  }

  @DgsQuery
  @PreAuthorize("isAuthenticated() and #businessId == authentication.principal.getUid()")
  public Business business(@InputArgument String businessId) {
    return modelMapper.map(businessService.getBusinessById(businessId), Business.class);
  }
}
