package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import us.congressionalappchallenge.scheduler.service.persistence.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
}
