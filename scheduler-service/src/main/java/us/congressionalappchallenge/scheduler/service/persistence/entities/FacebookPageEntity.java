package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "facebook_page")
public class FacebookPageEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @Column(name = "external_id")
  private String externalId;

  @Column(name = "name")
  private String name;

  @Column(name = "access_token")
  private String accessToken;
}
