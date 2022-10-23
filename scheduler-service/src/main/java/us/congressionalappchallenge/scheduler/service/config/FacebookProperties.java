package us.congressionalappchallenge.scheduler.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "facebook")
@Data
public class FacebookProperties {
  private String appId;
  private String appSecret;
  private String facebookCallbackUrl;
  private String instagramCallbackUrl;
  private String facebookPermissions;
  private String instagramPermissions;
}
