package us.congressionalappchallenge.scheduler.service.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerServiceConfig {
  @Bean
  ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper
        .getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(AccessLevel.PROTECTED)
        .setSourceNamingConvention(NamingConventions.NONE)
        .setDestinationNamingConvention(NamingConventions.NONE)
        .setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
        .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
        .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
        .setMatchingStrategy(MatchingStrategies.LOOSE)
        .setSkipNullEnabled(true);
    return modelMapper;
  }
}
