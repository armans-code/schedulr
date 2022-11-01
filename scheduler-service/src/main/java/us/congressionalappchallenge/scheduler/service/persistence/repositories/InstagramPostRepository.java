package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramPostEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterTweetEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface InstagramPostRepository extends JpaRepository<InstagramPostEntity, UUID> {
    @Query(value = "SELECT * FROM instagram_post WHERE business_id = :businessId", nativeQuery = true)
    List<InstagramPostEntity> findAllByBusinessId(UUID businessId);

    @Query(value = "SELECT * FROM instagram_post WHERE business_id = :businessId AND created_at <= :until AND created_at >= :since", nativeQuery = true)
    List<InstagramPostEntity> findAllByBusinessIdAndSinceAndUntil(UUID businessId, Date since, Date until);
}
