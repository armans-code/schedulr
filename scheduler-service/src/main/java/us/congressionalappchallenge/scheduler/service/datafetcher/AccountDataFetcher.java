package us.congressionalappchallenge.scheduler.service.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import us.congressionalappchallenge.scheduler.service.graphql.types.Account;
import us.congressionalappchallenge.scheduler.service.graphql.types.RegisterAccountInput;
import us.congressionalappchallenge.scheduler.service.services.AccountService;

import java.util.UUID;

@DgsComponent
public class AccountDataFetcher {

  private final AccountService accountService;

  public AccountDataFetcher(AccountService accountService) {
    this.accountService = accountService;
  }

  @DgsMutation
  public Account registerAccount(@InputArgument RegisterAccountInput registerInput) {
    return accountService.registerAccount(registerInput);
  }

  @DgsQuery
  public Account getAccount(@InputArgument String id) {
    return accountService.getAccount(UUID.fromString(id));
  }
}
