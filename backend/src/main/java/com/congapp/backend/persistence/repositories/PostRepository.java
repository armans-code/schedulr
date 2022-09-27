package com.congapp.backend.persistence.repositories;

import com.congapp.backend.persistence.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
}
