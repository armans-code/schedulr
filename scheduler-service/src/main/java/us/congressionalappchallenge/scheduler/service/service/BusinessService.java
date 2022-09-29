package us.congressionalappchallenge.scheduler.service.service;

import com.google.firebase.auth.UserRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;

@Service
public class BusinessService {
  private final BusinessRepository businessRepository;
  private final ModelMapper modelMapper;
  private final AuthService authService;

  public BusinessService(BusinessRepository businessRepository, ModelMapper modelMapper, AuthService authService) {
    this.businessRepository = businessRepository;
    this.modelMapper = modelMapper;
    this.authService = authService;
  }

  public Business registerBusiness(RegisterBusinessInput registerAccountInput) {
    UserRecord newUser = authService.createUser(registerAccountInput.getEmail(), registerAccountInput.getPassword());
    registerAccountInput.setAuthId(newUser.getUid());
    BusinessEntity businessEntity = modelMapper.map(registerAccountInput, BusinessEntity.class);
    return modelMapper.map(businessRepository.save(businessEntity), Business.class);
  }

  public Business getBusinessByAuthId(String authId) {
    BusinessEntity businessEntity = businessRepository.findByAuthId(authId).orElseThrow(() -> new IllegalArgumentException(
            "Business entity with AuthId " + authId + " not found."
    ));
    return modelMapper.map(businessEntity, Business.class);
  }
}
