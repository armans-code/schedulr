package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FacebookAccountRepository extends JpaRepository<FacebookAccountEntity, UUID> {
  @Query(name = "SELECT * FROM facebook_account WHERE business_id = :businessId", nativeQuery = true)
  List<FacebookAccountEntity> findAllByBusinessId(UUID businessId);

  @Query(name = "SELECT * FROM facebook_account WHERE facebook_id = :facebookId", nativeQuery = true)
  Optional<FacebookAccountEntity> findByFacebookId(String facebookId);

  @Query(value = "SELECT * FROM facebook_account WHERE id = :id AND business_id = :businessId", nativeQuery = true)
  Optional<FacebookAccountEntity> findByIdAndBusinessId(UUID id, UUID businessId);
}
