package us.congressionalappchallenge.scheduler.service.service;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.PostUpdate;
import facebook4j.auth.AccessToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.graphql.types.Business;
import us.congressionalappchallenge.scheduler.service.graphql.types.CreateFacebookPostInput;
import us.congressionalappchallenge.scheduler.service.graphql.types.FacebookPost;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookPostEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookAccountRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookPostRepository;
import us.congressionalappchallenge.scheduler.service.util.DateUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

@Service
public class PostService {
  private static final Log log = LogFactory.getLog(PostService.class);
  private final BusinessRepository businessRepository;
  private final FacebookPostRepository facebookPostRepository;
  private final FacebookAccountRepository facebookAccountRepository;
  private final Facebook facebook;
  private final ModelMapper modelMapper;

  public PostService(
      BusinessRepository businessRepository,
      FacebookPostRepository facebookPostRepository,
      FacebookAccountRepository facebookAccountRepository,
      Facebook facebook,
      ModelMapper modelMapper) {
    this.businessRepository = businessRepository;
    this.facebookPostRepository = facebookPostRepository;
    this.facebookAccountRepository = facebookAccountRepository;
    this.facebook = facebook;
    this.modelMapper = modelMapper;
  }

  public FacebookPost createFacebookPost(String userId, CreateFacebookPostInput input) {
    FacebookPost.Builder responseBuilder = FacebookPost.newBuilder();
    BusinessEntity business = findBusinessById(userId);
    responseBuilder.business(modelMapper.map(business, Business.class));
    FacebookAccountEntity facebookAccount =
        findFacebookAccount(UUID.fromString(input.getFacebookAccountId()), business.getId());
    String id = sendFacebook(input, facebookAccount);
    log.info("Facebook Created/Scheduled Post ID: " + id);
    FacebookPostEntity savedPost = saveFacebook(business, facebookAccount, id, input);
    modelMapper.map(savedPost, responseBuilder);
    return responseBuilder.build();
  }

  private String sendFacebook(CreateFacebookPostInput input, FacebookAccountEntity facebookAccount) {
    try {
      PostUpdate postUpdate = new PostUpdate(input.getMessage());
      if (!Objects.isNull(input.getLink())) postUpdate.setLink(new URL(input.getLink()));
      if (!Objects.isNull(input.getTags())) postUpdate.setTags(input.getTags());
      if (!Objects.isNull(input.getPlace())) postUpdate.setPlace(input.getPlace());
      if (!Objects.isNull(input.getScheduledPublishTime())) {
        postUpdate.setScheduledPublishTime(DateUtil.convert(input.getScheduledPublishTime()));
        postUpdate.setPublished(false);
      }
      facebook.setOAuthAccessToken(new AccessToken(facebookAccount.getAccessToken()));
      return facebook.postFeed(postUpdate);
    } catch (FacebookException e) {
      throw new RuntimeException("Facebook Error: " + e);
    } catch (MalformedURLException e) {
      throw new RuntimeException("Malformed Error: " + e);
    }
  }

  private FacebookPostEntity saveFacebook(
      BusinessEntity business,
      FacebookAccountEntity facebookAccount,
      String id,
      CreateFacebookPostInput input) {
    FacebookPostEntity postEntity = modelMapper.map(input, FacebookPostEntity.class);
    postEntity.setBusiness(business);
    postEntity.setFacebookAccount(facebookAccount);
    postEntity.setFacebookId(id);
    if (!input.getScheduledPublishTime().isEmpty())
      postEntity.setScheduledAt(DateUtil.convert(input.getScheduledPublishTime()));
    return facebookPostRepository.save(postEntity);
  }

  private BusinessEntity findBusinessById(String userId) {
    return businessRepository
        .findById(UUID.fromString(userId))
        .orElseThrow(() -> new RuntimeException("Business Not Found for Id: " + userId));
  }

  private FacebookAccountEntity findFacebookAccount(UUID accountId, UUID businessId) {
    return facebookAccountRepository
        .findByIdAndOrganizationId(accountId, businessId)
        .orElseThrow(() -> new RuntimeException("Facebook Account Not Found for Business Id: " + businessId + " Account Id: " + accountId));
  }
}
