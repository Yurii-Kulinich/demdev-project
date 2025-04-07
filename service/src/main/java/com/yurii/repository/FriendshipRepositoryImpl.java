package com.yurii.repository;

import com.yurii.entity.Friendship;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class FriendshipRepositoryImpl extends CustomRepositoryImpl<UUID, Friendship> {

  protected FriendshipRepositoryImpl(EntityManager em) {
    super(Friendship.class, em);
  }
}
