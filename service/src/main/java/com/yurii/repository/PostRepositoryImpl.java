package com.yurii.repository;

import com.yurii.entity.Post;
import jakarta.persistence.EntityManager;
import java.util.UUID;

public class PostRepositoryImpl extends CustomRepositoryImpl<UUID, Post> {

  protected PostRepositoryImpl(EntityManager em) {
    super(Post.class, em);
  }
}
