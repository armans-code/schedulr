package us.congressionalappchallenge.scheduler.service.service;

import facebook4j.Account;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookAccount;
import us.congressionalappchallenge.scheduler.service.graphql.types.IntegrateFacebookInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookAccountRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class IntegrationService {
  private final Facebook facebook;
  private final FacebookAccountRepository facebookAccountRepository;
  private final BusinessRepository businessRepository;
  private final ModelMapper modelMapper;

  public IntegrationService(
      Facebook facebook,
      FacebookAccountRepository facebookAccountRepository,
      BusinessRepository businessRepository,
      ModelMapper modelMapper) {
    this.facebook = facebook;
    this.facebookAccountRepository = facebookAccountRepository;
    this.businessRepository = businessRepository;
    this.modelMapper = modelMapper;
  }

  public List<FacebookAccount> integrateFacebook(IntegrateFacebookInput input) {
    try {
      BusinessEntity business = findBusinessById(UUID.fromString(input.getBusinessId()));
      facebook.getOAuthAccessToken(input.getCode());
      return facebook.getAccounts().stream()
          .map(account ->modelMapper.map(saveFacebookAccount(account, business), FacebookAccount.class))
          .collect(Collectors.toList());
    } catch (FacebookException e) {
      throw new RuntimeException("Facebook error: " + e);
    }
  }

  private FacebookAccountEntity saveFacebookAccount(Account account, BusinessEntity organization) {
    FacebookAccountEntity accountEntity = new FacebookAccountEntity();
    accountEntity.setBusiness(organization);
    accountEntity.setName(account.getName());
    accountEntity.setFacebookId(account.getId());
    accountEntity.setAccessToken(account.getAccessToken());
    return facebookAccountRepository.save(accountEntity);
  }

  private BusinessEntity findBusinessById(UUID id) {
    return businessRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchElementException("Business not found for Id: " + id));
  }
}
