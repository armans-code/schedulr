package com.congapp.backend.persistence.repositories;

import com.congapp.backend.persistence.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
}
