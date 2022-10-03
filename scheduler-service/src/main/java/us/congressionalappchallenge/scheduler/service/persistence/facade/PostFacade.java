package us.congressionalappchallenge.scheduler.service.persistence.facade;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateFacebookPostInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateInstagramPostInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.*;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookPostRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.InstagramPostRepository;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

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
      CreateFacebookPostInput input,
      String postId) {
    FacebookPostEntity postEntity = new FacebookPostEntity();
    postEntity.setBusiness(business);
    postEntity.setFacebookAccount(facebookAccount);
    postEntity.setFacebookId(postId);
    postEntity.setMessage(input.getMessage());
    if (!input.getLink().isEmpty()) postEntity.setLink(input.getLink());
    if (!input.getScheduledPublishTime().isEmpty()) {
      postEntity.setScheduled(true);
      postEntity.setScheduledPublishTime(DateUtil.convert(input.getScheduledPublishTime()));
    } else {
      postEntity.setScheduled(false);
    }
    return facebookPostRepository.save(postEntity);
  }

  public InstagramPostEntity saveInstagramPost(
      BusinessEntity business,
      InstagramAccountEntity instagramAccount,
      CreateInstagramPostInput input, String instagramId, Boolean scheduled) {
    InstagramPostEntity postEntity = new InstagramPostEntity();
    postEntity.setBusiness(business);
    postEntity.setInstagramAccount(instagramAccount);
    postEntity.setCaption(input.getCaption());
    if (!input.getImageUrl().isEmpty()) postEntity.setImageUrl(input.getImageUrl());
    if (scheduled) {
      postEntity.setScheduled(true);
      postEntity.setScheduledPublishTime(DateUtil.convert(input.getScheduledPublishTime()));
    } else {
      postEntity.setInstagramId(instagramId);
      postEntity.setScheduled(false);
    }
    return instagramPostRepository.save(postEntity);
  }
}
