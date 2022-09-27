package com.congapp.backend.services;

import com.congapp.backend.persistence.entities.AccountEntity;
import com.congapp.backend.persistence.repositories.AccountRepository;
import com.example.events.eventapp.generated.types.Account;
import com.example.events.eventapp.generated.types.RegisterAccountInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@DgsComponent
public class AccountService {

    private final ModelMapper modelMapper;

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @DgsMutation
    public Account registerAccount(@InputArgument RegisterAccountInput registerInput) {
        AccountEntity accountEntity = modelMapper.map(registerInput, AccountEntity.class);
        return modelMapper.map(accountRepository.save(accountEntity), Account.class);
    }

    @DgsQuery
    public List<Account> getAccounts() {
        return accountRepository.findAll().stream().map((account) -> modelMapper.map(account, Account.class)).collect(Collectors.toList());
    }

}
