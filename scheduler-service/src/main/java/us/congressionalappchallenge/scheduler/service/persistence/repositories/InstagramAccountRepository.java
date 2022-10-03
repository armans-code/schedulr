package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstagramAccountRepository extends JpaRepository<InstagramAccountEntity, UUID> {
    @Query(name = "SELECT * FROM instagram_account WHERE instagram_id = :instagramId", nativeQuery = true)
    Optional<InstagramAccountEntity> findByInstagramId(String instagramId);

    @Query(name = "SELECT * FROM instagram_account WHERE business_id = :businessId", nativeQuery = true)
    List<InstagramAccountEntity> findAllByBusinessId(UUID businessId);

    @Query(value = "SELECT * FROM instagram_account WHERE id = :id AND business_id = :businessId", nativeQuery = true)
    Optional<InstagramAccountEntity> findByIdAndBusinessId(UUID id, UUID businessId);
}
