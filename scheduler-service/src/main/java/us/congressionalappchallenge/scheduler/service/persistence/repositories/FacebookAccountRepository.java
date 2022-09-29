package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;

import java.util.Optional;
import java.util.UUID;

public interface FacebookAccountRepository extends JpaRepository<FacebookAccountEntity, UUID> {
  @Query(
      value = "SELECT * FROM facebook_account WHERE id = :id AND business_id = :businessId",
      nativeQuery = true)
  Optional<FacebookAccountEntity> findByIdAndOrganizationId(UUID id, UUID businessId);
}
