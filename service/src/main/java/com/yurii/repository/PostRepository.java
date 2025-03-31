package com.yurii.repository;

import com.yurii.entity.Post;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository extends RepositoryBase<UUID, Post> {

  @Autowired
  public PostRepository(EntityManager em) {
    super(Post.class, em);
  }
}
