package us.congressionalappchallenge.scheduler.service.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;

import java.util.UUID;

@Service
public class BusinessService {
  private final BusinessRepository businessRepository;
  private final ModelMapper modelMapper;
  private final FirebaseAuth firebaseAuth;

  public BusinessService(
      BusinessRepository businessRepository, ModelMapper modelMapper, FirebaseAuth firebaseAuth) {
    this.businessRepository = businessRepository;
    this.modelMapper = modelMapper;
    this.firebaseAuth = firebaseAuth;
  }

  public Business registerBusiness(RegisterBusinessInput registerAccountInput) {
    UUID businessId = UUID.randomUUID();
    registerFirebaseUser(businessId, registerAccountInput.getEmail(), registerAccountInput.getPassword());
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setId(businessId);
    businessEntity.setName(registerAccountInput.getName());
    businessEntity.setEmail(registerAccountInput.getEmail());
    return modelMapper.map(businessRepository.save(businessEntity), Business.class);
  }

  public Business getBusinessById(String businessId) {
    BusinessEntity businessEntity =
        businessRepository
            .findById(UUID.fromString(businessId))
            .orElseThrow(() -> new IllegalArgumentException("Business entity with Id " + businessId + " not found."));
    return modelMapper.map(businessEntity, Business.class);
  }

  private UserRecord registerFirebaseUser(UUID businessId, String email, String password) {
    try {
      UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest();
      createRequest.setUid(businessId.toString());
      createRequest.setEmail(email);
      createRequest.setPassword(password);
      return firebaseAuth.createUser(createRequest);
    } catch (FirebaseAuthException e) {
      throw new RuntimeException("Firebase Error: " + e);
    }
  }
}
