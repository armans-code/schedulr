package us.congressionalappchallenge.scheduler.service.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterBusinessInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class BusinessService {
  private final AccountFacade accountFacade;
  private final FirebaseAuth firebaseAuth;

  @Transactional
  public BusinessEntity registerBusiness(RegisterBusinessInput registerAccountInput) {
    BusinessEntity businessEntity = new BusinessEntity();
    businessEntity.setName(registerAccountInput.getName());
    businessEntity.setEmail(registerAccountInput.getEmail());
    accountFacade.getBusinessRepository().save(businessEntity);
    registerFirebaseUser(
        businessEntity.getId(),
        registerAccountInput.getEmail(),
        registerAccountInput.getPassword());
    return businessEntity;
  }

  public BusinessEntity getBusinessById(String businessId) {
    return accountFacade.findBusinessById(UUID.fromString(businessId));
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
