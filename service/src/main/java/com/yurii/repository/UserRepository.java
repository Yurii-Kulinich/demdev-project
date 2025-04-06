package com.yurii.repository;

import com.yurii.entity.User;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends RepositoryBase<UUID, User> {

  public UserRepository(EntityManager em) {
    super(User.class, em);
  }
}
