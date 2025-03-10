package com.yurii.repository;

import com.yurii.dao.FriendshipRepository;
import com.yurii.entity.Friendship;
import com.yurii.entity.Role;
import com.yurii.entity.Status;
import com.yurii.entity.User;
import com.yurii.integration.IntegrationTestBase;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FriendshipRepositoryTest extends IntegrationTestBase {
  private FriendshipRepository friendshipRepository;
  private User user;
  private User friend;

  @BeforeEach
  void init() {
    friendshipRepository = new FriendshipRepository(session);
    user = getUser("user1@example.com");
    friend = getUser("user2@example.com");

    session.persist(user);
    session.persist(friend);
    session.flush();
  }

  @Test
  void save_shouldPersistFriendship() {
    Friendship friendship = getFriendship(user, friend);
    friendshipRepository.save(friendship);
    session.flush();
    session.clear();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(friendship.getId());

    Assertions.assertTrue(foundFriendship.isPresent());
  }

  @Test
  void delete_shouldRemoveFriendship() {
    Friendship friendship = getFriendship(user, friend);
    friendshipRepository.save(friendship);
    session.flush();
    friendshipRepository.delete(friendship.getId());
    session.flush();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(friendship.getId());

    Assertions.assertTrue(foundFriendship.isEmpty());
  }

  @Test
  void update_shouldModifyExistingFriendship() {
    Friendship friendship = getFriendship(user, friend);
    friendshipRepository.save(friendship);
    session.flush();
    friendship.setStatus(Status.CONFIRMED);
    friendshipRepository.update(friendship);
    session.flush();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(friendship.getId());

    Assertions.assertTrue(foundFriendship.isPresent());
    Assertions.assertEquals(Status.CONFIRMED, foundFriendship.get().getStatus());
  }

  @Test
  void findById_shouldReturnExistingFriendship() {
    Friendship friendship = getFriendship(user, friend);
    var savedFriendship = friendshipRepository.save(friendship);
    session.flush();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(savedFriendship.getId());

    Assertions.assertTrue(foundFriendship.isPresent());
  }

  @Test
  void findAll_shouldReturnMultipleFriendships() {
    List<Friendship> friendships = friendshipRepository.findAll();

    Assertions.assertFalse(friendships.isEmpty());
    Assertions.assertTrue(friendships.size() >= 5);
  }

  private static User getUser(String email) {
    return User.builder()
        .password("testPass")
        .firstName("TestName")
        .lastName("TestLast")
        .email(email)
        .role(Role.USER)
        .birthDate(java.time.LocalDate.of(1995, 5, 15))
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }

  private static Friendship getFriendship(User user, User friend) {
    return Friendship.builder()
        .user(user)
        .friend(friend)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }
}
