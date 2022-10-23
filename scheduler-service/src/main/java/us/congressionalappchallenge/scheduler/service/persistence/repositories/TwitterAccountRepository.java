package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TwitterAccountRepository extends JpaRepository<TwitterAccountEntity, UUID> {
    @Query(name = "SELECT * FROM twitter_account WHERE business_id = :businessId", nativeQuery = true)
    List<TwitterAccountEntity> findAllByBusinessId(UUID businessId);

    @Query(value = "SELECT * FROM twitter_account WHERE twitter_id = :twitterId", nativeQuery = true)
    Optional<TwitterAccountEntity> findByTwitterId(String twitterId);

    @Query(value = "SELECT * FROM twitter_account WHERE id = :twitterId AND business_id: businessId", nativeQuery = true)
    Optional<TwitterAccountEntity> findByIdAndBusinessId(UUID id, UUID businessId);

}
