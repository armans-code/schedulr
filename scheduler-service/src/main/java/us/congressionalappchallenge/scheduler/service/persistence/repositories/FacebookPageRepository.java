package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookPageEntity;

import java.util.Optional;
import java.util.UUID;

public interface FacebookPageRepository extends JpaRepository<FacebookPageEntity, UUID> {
  @Query(
      value = "SELECT * FROM facebook_page WHERE id = :id AND business_id = :businessId",
      nativeQuery = true
  )
  Optional<FacebookPageEntity> findByIdAndBusinessId(UUID id, UUID businessId);
}
