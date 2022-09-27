package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import us.congressionalappchallenge.scheduler.service.persistence.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
}
