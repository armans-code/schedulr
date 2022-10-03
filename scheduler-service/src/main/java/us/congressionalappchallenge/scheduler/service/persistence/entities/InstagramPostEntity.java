package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "instagram_post")
@Data
public class InstagramPostEntity extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  @ManyToOne
  @JoinColumn(name = "instagram_account_id", nullable = false)
  private InstagramAccountEntity instagramAccount;

  @Column(name = "job_id")
  private String jobId;

  @Column(name = "instagram_id")
  private String instagramId;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "caption")
  private String caption;

  @Column(name = "scheduled")
  private Boolean scheduled;

  @Column(name = "scheduled_publish_time")
  private Date scheduledPublishTime;
}
