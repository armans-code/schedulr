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
  @JoinColumn(name = "account_id", nullable = false)
  private AccountEntity account;

  @Column(name = "message")
  private String message;

  @Column(name = "providers")
  @Enumerated(value = EnumType.STRING)
  private ProviderType providerType;

  @Column(name = "post_id")
  private String postId;
}
