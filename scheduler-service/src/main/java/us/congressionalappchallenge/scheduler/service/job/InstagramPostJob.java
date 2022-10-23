package us.congressionalappchallenge.scheduler.service.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import us.congressionalappchallenge.scheduler.service.helper.InstagramHelper;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramPostEntity;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;
import us.congressionalappchallenge.scheduler.service.persistence.facade.PostFacade;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class InstagramPostJob extends QuartzJobBean {
  private final InstagramHelper instagramHelper;
  private final PostFacade postFacade;
  private final AccountFacade accountFacade;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    JobDataMap dataMap = context.getMergedJobDataMap();
    InstagramAccountEntity instagramAccount =
        accountFacade.findInstagramAccount(
            (UUID) dataMap.get("instagram-account-id"), (UUID) dataMap.get("business-id"));
    InstagramPostEntity instagramPost =
        postFacade.findInstagramPost((UUID) dataMap.get("instagram-post-id"));
    Optional<String> imageUrlOpt = Optional.ofNullable(instagramPost.getImageUrl());
    String postId =
        instagramHelper.sendInstagramPost(
            instagramPost.getCaption(), imageUrlOpt, instagramAccount);
    instagramPost.setInstagramId(postId);
    instagramPost.setScheduled(false);
    postFacade.getInstagramPostRepository().save(instagramPost);
  }
}
