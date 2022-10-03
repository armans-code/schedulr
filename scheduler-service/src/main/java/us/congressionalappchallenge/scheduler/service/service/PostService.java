package us.congressionalappchallenge.scheduler.service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateFacebookPostInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateInstagramPostInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.QueryFilter;
import us.congressionalappchallenge.scheduler.service.helper.FacebookHelper;
import us.congressionalappchallenge.scheduler.service.helper.InstagramHelper;
import us.congressionalappchallenge.scheduler.service.job.JobService;
import us.congressionalappchallenge.scheduler.service.persistence.entities.*;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;
import us.congressionalappchallenge.scheduler.service.persistence.facade.PostFacade;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
  private final FacebookHelper facebookHelper;
  private final InstagramHelper instagramHelper;
  private final AccountFacade accountFacade;
  private final PostFacade postFacade;
  private final JobService jobService;

  public List<FacebookPostEntity> getFacebookPosts(QueryFilter queryFilter) {
    return postFacade
        .getFacebookPostRepository()
        .findByBusinessId(
            UUID.fromString(queryFilter.getBusinessId()),
            DateUtil.convert(queryFilter.getSince()),
            DateUtil.convert(queryFilter.getUntil()));
  }

  public FacebookPostEntity createFacebookPost(CreateFacebookPostInput input) {
    BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(input.getBusinessId()));
    FacebookAccountEntity facebookAccount =
        accountFacade.findFacebookAccount(
            UUID.fromString(input.getFacebookAccountId()), business.getId());
    String postId = facebookHelper.sendFacebookPost(input, facebookAccount);
    log.info("instagram created post id: " + postId);
    return postFacade.saveFacebookPost(business, facebookAccount, input, postId);
  }

  public InstagramPostEntity createInstagramPost(CreateInstagramPostInput input) {
    BusinessEntity business = accountFacade.findBusinessById(UUID.fromString(input.getBusinessId()));
    InstagramAccountEntity instagramAccount = accountFacade
            .findInstagramAccount(UUID.fromString(input.getInstagramAccountId()), business.getId());
    if (Objects.isNull(input.getScheduledPublishTime())) {
      InstagramPostEntity postEntity = postFacade
              .saveInstagramPost(business, instagramAccount, input, null, true);
      jobService.scheduleInstagramPostJob(input, instagramAccount, postEntity);
      return postEntity;
    } else {
      String postId = instagramHelper.sendInstagramPost(input, instagramAccount);
      return postFacade.saveInstagramPost(business, instagramAccount, input, postId, false);
    }
  }
}
