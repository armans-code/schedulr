package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "instagram_account")
@Data
public class InstagramAccountEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @ManyToOne
  @JoinColumn(name = "facebook_account_id", nullable = false)
  private FacebookAccountEntity facebookAccount;

  @Column(name = "instagram_id")
  private String instagramId;
}
