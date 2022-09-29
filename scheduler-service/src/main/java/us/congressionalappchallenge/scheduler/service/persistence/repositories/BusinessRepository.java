package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository extends JpaRepository<BusinessEntity, UUID> {
  @Query(value = "SELECT * FROM business WHERE auth_id = :authId", nativeQuery = true)
  Optional<BusinessEntity> findByAuthId(String authId);
}
