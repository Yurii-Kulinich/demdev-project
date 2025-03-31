package com.yurii.repository;

import com.yurii.entity.Friendship;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FriendshipRepository extends RepositoryBase<UUID, Friendship> {

  @Autowired
  public FriendshipRepository(EntityManager em) {
    super(Friendship.class, em);
  }

}
