package com.yurii.repository;

import com.yurii.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID>, CustomRepository<UUID, User> {

}
