package us.congressionalappchallenge.scheduler.service.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateInstagramPostInput;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramPostEntity;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class JobService {
  private final SchedulerFactoryBean schedulerFactory;

  public void scheduleInstagramPostJob(
          CreateInstagramPostInput input, InstagramAccountEntity instagramAccount, InstagramPostEntity instagramPost) {
    try {
      JobDataMap dataMap = new JobDataMap();
      dataMap.put("instagram-input", input);
      dataMap.put("instagram-account", instagramAccount);
      dataMap.put("instagram-post", instagramPost);
      JobDetail jobDetail = buildJobDetail(dataMap);
      Trigger trigger = buildTrigger(jobDetail, DateUtil.convert(input.getScheduledPublishTime()));
      Date scheduledDate = schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
      log.info("Instagram post with business ID {} scheduled {}", input.getBusinessId(), scheduledDate);
    } catch (SchedulerException e) {
      throw new RuntimeException("Job scheduler error: " + e);
    }
  }

  private JobDetail buildJobDetail(JobDataMap dataMap) {
    return JobBuilder.newJob(InstagramPostJob.class)
            .withIdentity("job")
            .setJobData(dataMap).build();
  }

  private Trigger buildTrigger(JobDetail jobDetail, Date startAt) {
    return TriggerBuilder.newTrigger()
        .withIdentity("trigger")
        .forJob(jobDetail)
        .startAt(startAt)
        .build();
  }
}
