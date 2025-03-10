package com.yurii.dao;

import com.yurii.entity.Post;
import jakarta.persistence.EntityManager;
import java.util.UUID;

public class PostRepository extends RepositoryBase<UUID, Post> {

  public PostRepository(EntityManager entityManager) {
    super(Post.class, entityManager);
  }
}
