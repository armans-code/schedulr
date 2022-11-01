package us.congressionalappchallenge.scheduler.service.job;

import com.twitter.clientlib.model.TweetCreateResponseData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import us.congressionalappchallenge.scheduler.service.helper.InstagramHelper;
import us.congressionalappchallenge.scheduler.service.helper.TwitterHelper;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramPostEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterTweetEntity;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;
import us.congressionalappchallenge.scheduler.service.persistence.facade.PostFacade;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class TwitterTweetJob extends QuartzJobBean {
    private final TwitterHelper twitterHelper;
    private final PostFacade postFacade;
    private final AccountFacade accountFacade;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        Optional<String> imageUrlOpt = Optional.ofNullable((String) dataMap.get("twitter-image-url"));
        TwitterAccountEntity twitterAccount =
                accountFacade.findTwitterAccount(
                        (UUID) dataMap.get("twitter-account-id"), (UUID) dataMap.get("business-id"));
        TwitterTweetEntity twitterTweet =
                postFacade.findTwitterTweet((UUID) dataMap.get("twitter-tweet-id"));
        // TODO handle uploading images
        TweetCreateResponseData tweetRes =
                twitterHelper.sendTwitterTweet(
                        twitterTweet.getMessage(), imageUrlOpt, twitterAccount);
        twitterTweet.setTweetId(tweetRes.getId());
        twitterTweet.setScheduled(false);
        postFacade.getTwitterTweetRepository().save(twitterTweet);
    }
}
