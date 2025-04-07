package com.yurii.repository;

import com.yurii.entity.User;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends CustomRepositoryImpl<UUID, User> {

  public UserRepositoryImpl(EntityManager em) {
    super(User.class, em);
  }
}
