package us.congressionalappchallenge.scheduler.service.service;

import com.twitter.clientlib.model.TweetCreateResponse;
import com.twitter.clientlib.model.TweetCreateResponseData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.*;
import us.congressionalappchallenge.scheduler.service.helper.FacebookHelper;
import us.congressionalappchallenge.scheduler.service.helper.InstagramHelper;
import us.congressionalappchallenge.scheduler.service.helper.TwitterHelper;
import us.congressionalappchallenge.scheduler.service.job.JobService;
import us.congressionalappchallenge.scheduler.service.persistence.entities.*;
import us.congressionalappchallenge.scheduler.service.persistence.facade.AccountFacade;
import us.congressionalappchallenge.scheduler.service.persistence.facade.PostFacade;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
  private final FacebookHelper facebookHelper;
  private final InstagramHelper instagramHelper;
  private final TwitterHelper twitterHelper;
  private final AccountFacade accountFacade;
  private final PostFacade postFacade;
  private final JobService jobService;

  public FacebookPostEntity createFacebookPost(CreateFacebookPostInput input) {
    BusinessEntity business =
        accountFacade.findBusinessById(UUID.fromString(input.getBusinessId()));
    FacebookAccountEntity facebookAccount =
        accountFacade.findFacebookAccount(
            UUID.fromString(input.getFacebookAccountId()), business.getId());
    Optional<String> linkOpt = Optional.ofNullable(input.getLink());
    Optional<String> tagsOpt = Optional.ofNullable(input.getTags());
    Optional<String> placeOpt = Optional.ofNullable(input.getPlace());
    Optional<String> scheduledPublishTimeOpt = Optional.ofNullable(input.getScheduledPublishTime());
    String postId =
        facebookHelper.sendFacebookPost(
            input.getMessage(),
            linkOpt,
            tagsOpt,
            placeOpt,
            scheduledPublishTimeOpt,
            facebookAccount);
    log.info("facebook created post id: " + postId);
    if (scheduledPublishTimeOpt.isPresent()) {
      return postFacade.saveFacebookPost(
          business,
          facebookAccount,
          input.getMessage(),
          linkOpt,
          tagsOpt,
          placeOpt,
          DateUtil.convert(scheduledPublishTimeOpt.get()));
    } else {
      return postFacade.saveFacebookPost(
          business, facebookAccount, input.getMessage(), linkOpt, tagsOpt, placeOpt, postId);
    }
  }

  public InstagramPostEntity createInstagramPost(CreateInstagramPostInput input) {
    BusinessEntity business =
        accountFacade.findBusinessById(UUID.fromString(input.getBusinessId()));
    InstagramAccountEntity instagramAccount =
        accountFacade.findInstagramAccount(
            UUID.fromString(input.getInstagramAccountId()), business.getId());
    Optional<String> scheduledPublishTimeOpt = Optional.ofNullable(input.getScheduledPublishTime());
    Optional<String> imageUrlOpt = Optional.ofNullable(input.getImageUrl());
    if (scheduledPublishTimeOpt.isPresent()) {
      InstagramPostEntity postEntity =
          postFacade.saveInstagramPost(
              business,
              instagramAccount,
              input.getCaption(),
              imageUrlOpt,
              DateUtil.convert(scheduledPublishTimeOpt.get()));
      jobService.scheduleInstagramPostJob(instagramAccount, postEntity);
      return postEntity;
    } else {
      String instagramId =
          instagramHelper.sendInstagramPost(input.getCaption(), imageUrlOpt, instagramAccount);
      return postFacade.saveInstagramPost(
          business, instagramAccount, input.getCaption(), imageUrlOpt, instagramId);
    }
  }

  public TwitterTweetEntity createTwitterTweet(CreateTwitterTweetInput input) {
    BusinessEntity business =
            accountFacade.findBusinessById(UUID.fromString(input.getBusinessId()));
    TwitterAccountEntity twitterAccount =
            accountFacade.findTwitterAccount(
                    UUID.fromString(input.getTwitterAccountId()), business.getId());
    Optional<String> scheduledPublishTimeOpt = Optional.ofNullable(input.getScheduledPublishTime());
    Optional<String> imageUrlOpt = Optional.ofNullable(input.getImageUrl());
    if (scheduledPublishTimeOpt.isPresent()) {
      TwitterTweetEntity postEntity =
              postFacade.saveTwitterTweet(
                      business,
                      twitterAccount,
                      input.getMessage(),
                      imageUrlOpt,
                      DateUtil.convert(scheduledPublishTimeOpt.get()));
      jobService.scheduleTwitterTweetJob(twitterAccount, postEntity);
      return postEntity;
    } else {
      TweetCreateResponseData twitterRes =
              twitterHelper.sendTwitterTweet(input.getMessage(), imageUrlOpt, twitterAccount);
      return postFacade.saveTwitterTweet(
              business, twitterAccount, input.getMessage(), imageUrlOpt, twitterRes.getId());
    }
  }

  public List<FacebookPostEntity> getFacebookPosts(QueryFilter queryFilter) {
    return postFacade
        .getFacebookPostRepository()
        .findAllByBusinessId(
            UUID.fromString(queryFilter.getBusinessId()));
  }

  public List<InstagramPostEntity> getInstagramPosts(QueryFilter queryFilter) {
    return postFacade
        .getInstagramPostRepository()
        .findAllByBusinessId(
            UUID.fromString(queryFilter.getBusinessId()));
  }

  public List<TwitterTweetEntity> getTwitterTweets(QueryFilter queryFilter) {
    return postFacade
            .getTwitterTweetRepository()
            .findAllByBusinessId(
                    UUID.fromString(queryFilter.getBusinessId()));
  }
}
