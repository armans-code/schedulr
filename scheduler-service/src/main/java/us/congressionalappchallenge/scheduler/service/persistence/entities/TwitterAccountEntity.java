package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "twitter_account")
public class TwitterAccountEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @Column(name = "twitter_id")
  private String twitterId;

  @Column(name = "name")
  private String name;

  @Column(name = "token")
  private String token;

  @Column(name = "token_secret")
  private String tokenSecret;
}
