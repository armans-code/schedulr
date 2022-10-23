package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "twitter_tweet")
@Data
public class TwitterTweetEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private BusinessEntity business;

    @ManyToOne
    @JoinColumn(name = "twitter_account_id", nullable = false)
    private TwitterAccountEntity twitterAccount;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "tweet_id")
    private String tweetId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "message")
    private String message;

    @Column(name = "scheduled")
    private Boolean scheduled;

    @Column(name = "scheduled_publish_time")
    private Date scheduledPublishTime;
}

