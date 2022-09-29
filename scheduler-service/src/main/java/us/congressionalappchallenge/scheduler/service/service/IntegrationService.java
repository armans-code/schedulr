package us.congressionalappchallenge.scheduler.service.service;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookPage;
import us.congressionalappchallenge.scheduler.service.graphql.types.IntegrateFacebookInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookPageEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookPageRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class IntegrationService {
  private final Facebook facebook;
  private final FacebookPageRepository facebookPageRepository;
  private final BusinessRepository businessRepository;
  private final ModelMapper modelMapper;

  public IntegrationService(
      Facebook facebook,
      FacebookPageRepository facebookPageRepository,
      BusinessRepository businessRepository,
      ModelMapper modelMapper) {
    this.facebook = facebook;
    this.facebookPageRepository = facebookPageRepository;
    this.businessRepository = businessRepository;
    this.modelMapper = modelMapper;
  }

  public List<FacebookPage> integrateFacebook(IntegrateFacebookInput input) {
    try {
      facebook.getOAuthAccessToken(input.getCode());
      return facebook.getAccounts().stream()
          .map(
              account -> {
                FacebookPageEntity pageEntity = new FacebookPageEntity();
                pageEntity.setBusiness(findBusinessById(UUID.fromString(input.getBusinessId())));
                pageEntity.setName(account.getName());
                pageEntity.setExternalId(account.getId());
                pageEntity.setAccessToken(account.getAccessToken());
                return modelMapper.map(facebookPageRepository.save(pageEntity), FacebookPage.class);
              })
          .collect(Collectors.toList());
    } catch (FacebookException e) {
      throw new RuntimeException("Facebook error: " + e);
    }
  }

  private BusinessEntity findBusinessById(UUID id) {
    return businessRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Business not found for ID: " + id));
  }
}
