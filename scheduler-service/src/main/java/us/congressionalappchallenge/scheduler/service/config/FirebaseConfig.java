package us.congressionalappchallenge.scheduler.service.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
public class FirebaseConfig {

  @Value("${firebase.project-id}")
  String projectId;

  @Value("${firebase.config-path}")
  String configPath;

  @Bean
  FirebaseApp firebaseApp() throws IOException {
    if (projectId == null || configPath == null) {
      log.error(
          "Firebase properties not configured properly. Please check firebase.* properties settings in configuration file.");
      throw new RuntimeException(
          "Firebase properties not configured properly. Please check firebase.* properties settings in configuration file.");
    }
    FileInputStream serviceAccountKey = new FileInputStream(configPath);
    FirebaseOptions options =
        FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccountKey))
            .setProjectId(projectId)
            .build();
    return FirebaseApp.initializeApp(options);
  }

  @Bean
  FirebaseAuth firebaseAuth() throws IOException {
    return FirebaseAuth.getInstance(firebaseApp());
  }
}
