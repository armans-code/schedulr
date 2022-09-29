package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @Column(name = "provider")
  private ProviderType provider;

  @Column(name = "external_id")
  private String externalId;

  @Column(name = "message")
  private String message;

  @Column(name = "tags")
  private String tags;

  @Column(name = "picture")
  private String picture;
}
