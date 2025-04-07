package com.yurii.repository;

import com.yurii.entity.Post;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, UUID>, CustomRepository<UUID, Post> {

}
