package us.congressionalappchallenge.scheduler.service.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateInstagramPostInput;
import us.congressionalappchallenge.scheduler.service.helper.InstagramHelper;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramPostEntity;
import us.congressionalappchallenge.scheduler.service.persistence.facade.PostFacade;

@AllArgsConstructor
@Slf4j
public class InstagramPostJob extends QuartzJobBean {
  private final InstagramHelper instagramHelper;
  private final PostFacade postFacade;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    JobDataMap jobDataMap = context.getMergedJobDataMap();
    CreateInstagramPostInput input =
        (CreateInstagramPostInput) jobDataMap.get("instagram-post-input");
    InstagramAccountEntity instagramAccount =
        (InstagramAccountEntity) jobDataMap.get("instagram-account");
    InstagramPostEntity instagramPost = (InstagramPostEntity) jobDataMap.get("instagram-post");
    String postId = instagramHelper.sendInstagramPost(input, instagramAccount);
    instagramPost.setInstagramId(postId);
    postFacade.getInstagramPostRepository().save(instagramPost);
  }
}
