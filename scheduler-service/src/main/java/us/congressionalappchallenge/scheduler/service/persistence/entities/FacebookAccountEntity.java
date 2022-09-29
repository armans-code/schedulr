package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "facebook_account")
public class FacebookAccountEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @Column(name = "facebook_id")
  private String facebookId;

  @Column(name = "name")
  private String name;

  @Column(name = "access_token")
  private String accessToken;
}
