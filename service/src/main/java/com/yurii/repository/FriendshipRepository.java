package com.yurii.repository;

import com.yurii.entity.Friendship;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, UUID>,
    CustomRepository<UUID, Friendship> {

}
