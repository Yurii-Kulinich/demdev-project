package com.yurii.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.yurii.entity.Friendship;
import com.yurii.entity.Role;
import com.yurii.entity.Status;
import com.yurii.entity.User;
import com.yurii.integration.IntegrationTestBase;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class FriendshipRepositoryTest extends IntegrationTestBase {

  @Autowired
  private FriendshipRepository friendshipRepository;
  @Autowired
  private EntityManager entityManager;
  private User user;
  private User friend;

  @BeforeEach
  void init() {
    user = createAndPersistUser("user1@example.com");
    friend = createAndPersistUser("user2@example.com");
    entityManager.flush();
  }

  @Test
  void save_shouldPersistFriendship() {
    Friendship friendship = createAndPersistFriendship(user, friend);
    friendshipRepository.save(friendship);
    entityManager.flush();
    entityManager.clear();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(friendship.getId());

    assertTrue(foundFriendship.isPresent());
  }

  @Test
  void delete_shouldRemoveFriendship() {
    Friendship friendship = createAndPersistFriendship(user, friend);
    friendshipRepository.save(friendship);
    entityManager.flush();
    friendshipRepository.delete(friendship);
    entityManager.flush();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(friendship.getId());

    assertTrue(foundFriendship.isEmpty());
  }

  @Test
  void update_shouldModifyExistingFriendship() {
    Friendship friendship = createAndPersistFriendship(user, friend);
    friendshipRepository.save(friendship);
    entityManager.flush();
    friendship.setStatus(Status.CONFIRMED);
    friendshipRepository.update(friendship);
    entityManager.flush();
    entityManager.clear();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(friendship.getId());

    assertTrue(foundFriendship.isPresent());
    assertEquals(Status.CONFIRMED, foundFriendship.get().getStatus());
  }

  @Test
  void findById_shouldReturnExistingFriendship() {
    Friendship friendship = createAndPersistFriendship(user, friend);
    var savedFriendship = friendshipRepository.save(friendship);
    entityManager.flush();

    Optional<Friendship> foundFriendship = friendshipRepository.findById(savedFriendship.getId());

    assertTrue(foundFriendship.isPresent());
  }

  private User createAndPersistUser(String email) {
    User user = User.builder()
        .password("testPass")
        .firstName("TestName")
        .lastName("TestLast")
        .email(email)
        .role(Role.USER)
        .birthDate(java.time.LocalDate.of(1995, 5, 15))
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    entityManager.persist(user);
    return user;
  }

  private Friendship createFriendship(User user, User friend) {
    return Friendship.builder()
        .user(user)
        .friend(friend)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .status(Status.CONFIRMED)
        .build();
  }

  private Friendship createAndPersistFriendship(User user, User friend) {
    Friendship friendship = createFriendship(user, friend);

    entityManager.persist(friendship);

    return friendship;
  }
}
