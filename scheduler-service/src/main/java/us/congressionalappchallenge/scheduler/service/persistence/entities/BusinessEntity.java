package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "business")
public class BusinessEntity extends BaseEntity {
  @Column(name = "auth_id")
  private String authId;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;
}
