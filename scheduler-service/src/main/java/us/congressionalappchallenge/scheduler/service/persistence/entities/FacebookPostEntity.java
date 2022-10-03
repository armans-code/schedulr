package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "facebook_post")
@Data
public class FacebookPostEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @ManyToOne
  @JoinColumn(name = "facebook_account_id", nullable = false)
  private FacebookAccountEntity facebookAccount;

  @Column(name = "facebook_id")
  private String facebookId;

  @Column(name = "message")
  private String message;

  @Column(name = "link")
  private String link;

  @Column(name = "tags")
  private String tags;

  @Column(name = "place")
  private String place;

  @Column(name = "scheduled")
  private Boolean scheduled;

  @Column(name = "scheduled_publish_time")
  private Date scheduledPublishTime;
}
