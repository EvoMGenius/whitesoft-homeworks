package com.evo.apatios.repository;

import com.evo.apatios.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findByName(String postName);
}
