package us.congressionalappchallenge.scheduler.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import us.congressionalappchallenge.scheduler.service.persistence.entities.BusinessEntity;

import java.util.UUID;

public interface BusinessRepository extends JpaRepository<BusinessEntity, UUID> {}
