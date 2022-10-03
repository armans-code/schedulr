package us.congressionalappchallenge.scheduler.service.persistence.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "business")
@Data
public class BusinessEntity extends BaseEntity {
  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;
}
