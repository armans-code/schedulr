package us.congressionalappchallenge.scheduler.service.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.helper.AuthHelper;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;

import java.util.UUID;

@Service
public class BusinessService {
  private final BusinessRepository businessRepository;
  private final ModelMapper modelMapper;
  private final AuthHelper authHelper;

  public BusinessService(
      BusinessRepository businessRepository, ModelMapper modelMapper, AuthHelper authHelper) {
    this.businessRepository = businessRepository;
    this.modelMapper = modelMapper;
    this.authHelper = authHelper;
  }

  public Business registerBusiness(RegisterBusinessInput registerAccountInput) {
    UUID businessId = UUID.randomUUID();
    authHelper.registerUser(businessId, registerAccountInput.getEmail(), registerAccountInput.getPassword());
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setId(businessId);
    businessEntity.setName(registerAccountInput.getName());
    businessEntity.setEmail(registerAccountInput.getEmail());
    return modelMapper.map(businessRepository.save(businessEntity), Business.class);
  }

  public Business getBusinessById(String userId) {
    BusinessEntity businessEntity =
        businessRepository
            .findById(UUID.fromString(userId))
            .orElseThrow(() -> new IllegalArgumentException("Business entity with Id " + userId + " not found."));
    return modelMapper.map(businessEntity, Business.class);
  }
}
