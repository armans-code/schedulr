package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "facebook_post")
public class FacebookPostEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @ManyToOne
  @JoinColumn(name = "facebook_account_id", nullable = false)
  private FacebookAccountEntity facebookAccount;

  @Column(name = "facebook_id")
  private String facebookId;

  @Column(name = "name")
  private String name;

  @Column(name = "message")
  private String message;

  @Column(name = "description")
  private String description;

  @Column(name = "caption")
  private String caption;

  @Column(name = "link")
  private String link;

  @Column(name = "tags")
  private String tags;

  @Column(name = "picture")
  private String picture;

  @Column(name = "place")
  private String place;

  @Column(name = "scheduled_at")
  private Date scheduledAt;
}
