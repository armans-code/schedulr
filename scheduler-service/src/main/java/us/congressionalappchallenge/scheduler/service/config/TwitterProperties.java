package us.congressionalappchallenge.scheduler.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twitter")
@Data
public class TwitterProperties {
    private String clientId;
    private String clientSecret;
    private String callbackUrl;
    private String scopes;
}