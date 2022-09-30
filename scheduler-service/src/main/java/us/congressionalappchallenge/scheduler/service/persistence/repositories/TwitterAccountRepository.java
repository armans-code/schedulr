package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;

import java.util.UUID;

public interface TwitterAccountRepository extends JpaRepository<TwitterAccountEntity, UUID> {}
