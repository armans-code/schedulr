package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FacebookPostRepository extends JpaRepository<FacebookPostEntity, UUID> {
}
