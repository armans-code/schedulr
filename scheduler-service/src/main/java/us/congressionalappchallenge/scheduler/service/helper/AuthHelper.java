package us.congressionalappchallenge.scheduler.service.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;

import java.util.UUID;

@Service
public class AuthHelper {
  private final FirebaseAuth firebaseAuth;

  public AuthHelper(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
  }

  public UserRecord registerUser(UUID businessId, String email, String password) {
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

  public FirebaseToken verifyUser(String idToken) {
    try {
      return firebaseAuth.verifyIdToken(idToken);
    } catch (FirebaseAuthException e) {
      throw new IllegalArgumentException("Unauthenticated User");
    }
  }

}
