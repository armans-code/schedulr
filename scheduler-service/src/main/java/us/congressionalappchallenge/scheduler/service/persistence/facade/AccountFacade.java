package us.congressionalappchallenge.scheduler.service.persistence.facade;

import com.facebook.ads.sdk.Page;
import com.github.scribejava.core.model.OAuth2AccessToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.FacebookAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.InstagramAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.entities.TwitterAccountEntity;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.BusinessRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.FacebookAccountRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.InstagramAccountRepository;
import us.congressionalappchallenge.scheduler.service.persistence.repositories.TwitterAccountRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@AllArgsConstructor
@Slf4j
public class AccountFacade {
    private final BusinessRepository businessRepository;
    private final FacebookAccountRepository facebookAccountRepository;
    private final InstagramAccountRepository instagramAccountRepository;
    private final TwitterAccountRepository twitterAccountRepository;

    public BusinessEntity findBusinessById(UUID id) {
        return businessRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Business not found for Id: " + id));
    }

    public FacebookAccountEntity saveFacebookAccount(Page page, BusinessEntity organization) {
        Optional<FacebookAccountEntity> facebookAccount =
                facebookAccountRepository.findByFacebookId(page.getId());
        if (facebookAccount.isEmpty()) {
            FacebookAccountEntity accountEntity = new FacebookAccountEntity();
            accountEntity.setBusiness(organization);
            accountEntity.setName(page.getFieldName());
            accountEntity.setFacebookId(page.getId());
            accountEntity.setAccessToken(page.getFieldAccessToken());
            return facebookAccountRepository.save(accountEntity);
        } else {
            return facebookAccount.get();
        }
    }

    public TwitterAccountEntity saveTwitterAccount(
            BusinessEntity business, OAuth2AccessToken accessToken, String twitterId, String twitterName) {
        Optional<TwitterAccountEntity> twitterAccount = twitterAccountRepository.findByTwitterId(twitterId);
        if (twitterAccount.isEmpty()) {
            TwitterAccountEntity accountEntity = new TwitterAccountEntity();
            accountEntity.setBusiness(business);
            accountEntity.setTwitterId(twitterId);
            accountEntity.setName(twitterName);
            accountEntity.setAccessToken(accessToken.getAccessToken());
            accountEntity.setRefreshToken(accessToken.getRefreshToken());
            return twitterAccountRepository.save(accountEntity);
        } else {
            return twitterAccount.get();
        }
    }

    public FacebookAccountEntity findFacebookAccount(UUID facebookAccountId, UUID businessId) {
        return facebookAccountRepository
                .findByIdAndBusinessId(facebookAccountId, businessId)
                .orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Facebook Account Not Found for ID: " + facebookAccountId));
    }

    public InstagramAccountEntity findInstagramAccount(UUID instagramAccountId, UUID businessId) {
        return instagramAccountRepository
                .findByIdAndBusinessId(instagramAccountId, businessId)
                .orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Instagram Account Not Found for ID: " + instagramAccountId));
    }

    public TwitterAccountEntity findTwitterAccount(UUID twitterAccountId, UUID businessId) {
        return twitterAccountRepository
                .findByIdAndBusinessId(twitterAccountId, businessId)
                .orElseThrow(
                        () ->
                                new NoSuchElementException(
                                        "Twitter Account Not Found for ID: " + twitterAccountId));
    }
}
