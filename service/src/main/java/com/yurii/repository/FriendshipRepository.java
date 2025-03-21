package com.yurii.repository;

import com.yurii.entity.Friendship;
import jakarta.persistence.EntityManager;
import java.util.UUID;

public class FriendshipRepository extends RepositoryBase<UUID, Friendship> {

  public FriendshipRepository(EntityManager entityManager) {
    super(Friendship.class, entityManager);
  }

}
