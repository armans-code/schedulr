package us.congressionalappchallenge.scheduler.service.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class BusinessService {
  private final BusinessRepository businessRepository;
  private final ModelMapper modelMapper;

  public BusinessService(BusinessRepository businessRepository, ModelMapper modelMapper) {
    this.businessRepository = businessRepository;
    this.modelMapper = modelMapper;
  }

  public Business registerBusiness(RegisterBusinessInput registerAccountInput) {
    BusinessEntity businessEntity = modelMapper.map(registerAccountInput, BusinessEntity.class);
    // TODO Handle Firebase Account Creation
    return modelMapper.map(businessRepository.save(businessEntity), Business.class);
  }
}
