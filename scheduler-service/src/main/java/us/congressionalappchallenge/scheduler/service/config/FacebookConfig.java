package us.congressionalappchallenge.scheduler.service.config;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacebookConfig {
  private static final Log log = LogFactory.getLog(FacebookConfig.class);

  @Value("${facebook.app-id}")
  private String appId;

  @Value("${facebook.app-secret}")
  private String appSecret;

  @Value("${facebook.permissions}")
  private String permissions;

  @Bean
  Facebook facebook() {
    if (appId == null || appSecret == null || permissions == null) {
      log.error(
          "Facebook properties not configured properly. Please check facebook.* properties settings in configuration file.");
      throw new RuntimeException(
          "Facebook properties not configured properly. Please check facebook.* properties settings in configuration file.");
    }

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
        .setOAuthAppId(appId)
        .setOAuthAppSecret(appSecret)
        .setOAuthPermissions(permissions);
    FacebookFactory ff = new FacebookFactory(cb.build());
    return ff.getInstance();
  }
}
