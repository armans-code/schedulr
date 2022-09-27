package us.congressionalappchallenge.scheduler.service.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import us.congressionalappchallenge.scheduler.service.graphql.types.Account;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterAccountInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.AccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.AccountRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class AccountService {
  private final ModelMapper modelMapper;
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
    this.accountRepository = accountRepository;
    this.modelMapper = modelMapper;
  }

  public Account registerAccount(RegisterAccountInput registerInput) {
    AccountEntity accountEntity = modelMapper.map(registerInput, AccountEntity.class);
    return modelMapper.map(accountRepository.save(accountEntity), Account.class);
  }

  public Account getAccount(UUID id) {
    AccountEntity entity =
        accountRepository
            .findById(id)
            .orElseThrow(() -> new NoSuchElementException("Account not found for ID: " + id));
    return modelMapper.map(entity, Account.class);
  }
}
