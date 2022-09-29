package us.congressionalappchallenge.scheduler.service.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final FirebaseAuth firebaseAuth;

    public AuthService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public UserRecord createUser(String email, String password) {
        try {
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest();
            createRequest.setEmail(email);
            createRequest.setPassword(password);
            return firebaseAuth.createUser(createRequest);
        } catch (FirebaseAuthException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
