package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramPostEntity;

import java.util.UUID;

@Repository
public interface InstagramPostRepository extends JpaRepository<InstagramPostEntity, UUID> {}
