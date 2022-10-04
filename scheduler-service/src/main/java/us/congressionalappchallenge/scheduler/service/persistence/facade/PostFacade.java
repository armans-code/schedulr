package us.congressionalappchallenge.scheduler.service.persistence.facade;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.persistence.entities.*;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookPostRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.InstagramPostRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@Slf4j
public class PostFacade {
  private final FacebookPostRepository facebookPostRepository;
  private final InstagramPostRepository instagramPostRepository;

  public PostFacade(
      FacebookPostRepository facebookPostRepository,
      InstagramPostRepository instagramPostRepository) {
    this.facebookPostRepository = facebookPostRepository;
    this.instagramPostRepository = instagramPostRepository;
  }

  public FacebookPostEntity saveFacebookPost(
      BusinessEntity business,
      FacebookAccountEntity facebookAccount,
      String message,
      Optional<String> link,
      Optional<String> tags,
      Optional<String> place,
      String facebookId) {
    FacebookPostEntity postEntity = new FacebookPostEntity();
    postEntity.setBusiness(business);
    postEntity.setFacebookAccount(facebookAccount);
    postEntity.setFacebookId(facebookId);
    postEntity.setMessage(message);
    link.ifPresent(postEntity::setLink);
    tags.ifPresent(postEntity::setTags);
    place.ifPresent(postEntity::setPlace);
    postEntity.setScheduled(false);
    return facebookPostRepository.save(postEntity);
  }

  public FacebookPostEntity saveFacebookPost(
      BusinessEntity business,
      FacebookAccountEntity facebookAccount,
      String message,
      Optional<String> link,
      Optional<String> tags,
      Optional<String> place,
      Date scheduledPublishTime) {
    FacebookPostEntity postEntity = new FacebookPostEntity();
    postEntity.setBusiness(business);
    postEntity.setFacebookAccount(facebookAccount);
    postEntity.setMessage(message);
    link.ifPresent(postEntity::setLink);
    tags.ifPresent(postEntity::setTags);
    place.ifPresent(postEntity::setPlace);
    postEntity.setScheduled(true);
    postEntity.setScheduledPublishTime(scheduledPublishTime);
    return facebookPostRepository.save(postEntity);
  }

  public InstagramPostEntity saveInstagramPost(
      BusinessEntity business,
      InstagramAccountEntity instagramAccount,
      String caption,
      Optional<String> imageUrl,
      String instagramId) {
    InstagramPostEntity postEntity = new InstagramPostEntity();
    postEntity.setBusiness(business);
    postEntity.setInstagramAccount(instagramAccount);
    postEntity.setCaption(caption);
    imageUrl.ifPresent(postEntity::setImageUrl);
    postEntity.setInstagramId(instagramId);
    postEntity.setScheduled(false);
    return instagramPostRepository.save(postEntity);
  }

  public InstagramPostEntity saveInstagramPost(
      BusinessEntity business,
      InstagramAccountEntity instagramAccount,
      String caption,
      Optional<String> imageUrl,
      Date publishedScheduledTime) {
    InstagramPostEntity postEntity = new InstagramPostEntity();
    postEntity.setBusiness(business);
    postEntity.setInstagramAccount(instagramAccount);
    postEntity.setCaption(caption);
    imageUrl.ifPresent(postEntity::setImageUrl);
    postEntity.setScheduledPublishTime(publishedScheduledTime);
    postEntity.setScheduled(true);
    return instagramPostRepository.save(postEntity);
  }

  public InstagramPostEntity findInstagramPost(UUID postId) {
    return instagramPostRepository
        .findById(postId)
        .orElseThrow(() -> new RuntimeException("Instagram Post not Found for ID: " + postId));
  }
}
