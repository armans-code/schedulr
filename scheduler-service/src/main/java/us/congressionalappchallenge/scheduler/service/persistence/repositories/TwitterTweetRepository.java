package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterTweetEntity;

import java.util.UUID;

public interface TwitterTweetRepository extends JpaRepository<TwitterTweetEntity, UUID> {

}
