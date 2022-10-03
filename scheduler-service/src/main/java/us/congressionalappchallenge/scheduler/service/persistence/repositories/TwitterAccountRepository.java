package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TwitterAccountRepository extends JpaRepository<TwitterAccountEntity, UUID> {
    @Query(value = "SELECT * FROM twitter_account WHERE twitter_id = :twitterId", nativeQuery = true)
    Optional<TwitterAccountEntity> findByTwitterId(String twitterId);
}
