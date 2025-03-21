package com.yurii.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.yurii.repository.UserDao;
import com.yurii.repository.UserFilter;
import com.yurii.integration.IntegrationTestBase;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserTest extends IntegrationTestBase {

  private final UserDao userDao = UserDao.getInstance();

  @Test
  void findByFirstNameAndFriendshipsNumber() {
    User user = getUser("mainuser@mail.com");
    User friend1 = getUser("firend1user@mail.com");
    User friend2 = getUser("firend2user@mail.com");

    session.persist(user);
    session.persist(friend1);
    session.persist(friend2);

    session.flush();

    Friendship friendship1 = Friendship.builder()
        .user(user)
        .friend(friend1)
        .build();
    Friendship friendship2 = Friendship.builder()
        .user(user)
        .friend(friend2)
        .build();

    UserFilter filter = UserFilter.builder()
        .email("mainuser@mail.com")
        .friendsNumber(1)
        .build();

    session.persist(friendship1);
    session.persist(friendship2);
    session.flush();
    session.clear();

    List<User> result = userDao.findAllByFirstNameAndFriendsNumber(session, filter);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(2, result.get(0).getInitiatedFriendships().size());
    assertEquals(user.getEmail(), result.get(0).getEmail());
  }

  @Test
  void findAveragePostLengthByEmailAndFriendsNumber(){
    User user = getUser("mainuser@mail.com");
    User friend1 = getUser("firend1user@mail.com");
    User friend2 = getUser("firend2user@mail.com");
    Post post = getPost(user);

    session.persist(user);
    session.persist(friend1);
    session.persist(friend2);
    session.persist(post);

    session.flush();

    Friendship friendship1 = Friendship.builder()
        .user(user)
        .friend(friend1)
        .build();
    Friendship friendship2 = Friendship.builder()
        .user(user)
        .friend(friend2)
        .build();

    UserFilter filter = UserFilter.builder()
        .email("mainuser@mail.com")
        .friendsNumber(1)
        .build();

    session.persist(friendship1);
    session.persist(friendship2);
    session.flush();
    session.clear();


    var result = userDao.findAveragePostLengthByEmailAndFriendsNumber(session, filter);

    assertNotNull(result);
    assertEquals(8, result);
  }

  @Test
  void whenPersistValidUser_thenShouldBeRetrievableFromDatabase() {
    User user = getUser("test@mail.com");

    session.persist(user);
    session.flush();

    User retrievedUser = session.get(User.class, user.getId());

    assertNotNull(retrievedUser);
    assertNotNull(retrievedUser.getId());

    assertEquals("TestName", retrievedUser.getFirstName());
    assertEquals("TestLast", retrievedUser.getLastName());
    assertEquals("test@mail.com", retrievedUser.getEmail());
    assertEquals(user.getRole(), retrievedUser.getRole());
    assertEquals(user.getBirthDate(), retrievedUser.getBirthDate());

  }

  @Test
  void whenUpdateUser_thenShouldReflectChanges() {
    User user = getUser("test@mail.com");

    session.persist(user);
    session.flush();

    user.setFirstName("UpdatedName");
    user.setLastName("UpdatedLast");
    session.merge(user);
    session.flush();

    User updatedUser = session.get(User.class, user.getId());

    assertNotNull(updatedUser);
    assertEquals("UpdatedName", updatedUser.getFirstName());
    assertEquals("UpdatedLast", updatedUser.getLastName());
  }

  @Test
  void whenDeleteUser_thenShouldNotExist() {
    User user = getUser("test@mail.com");

    session.persist(user);
    session.flush();

    var resultAfterPersist = session.get(User.class, user.getId());
    assertNotNull(resultAfterPersist);

    session.remove(user);
    session.flush();

    var result = session.get(User.class, user.getId());
    assertNull(result);
  }

  @Test
  void whenUserIsPersisted_thenPostIsPersistedToo() {
    User user = getUser("test@mail.com");

    Post post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    user.addPost(post);
    session.persist(user);
    session.flush();

    var retrievedPost = session.get(Post.class, post.getId());

    assertNotNull(retrievedPost);
    assertEquals(post.getId(), retrievedPost.getId());

  }

  @Test
  void whenPostIsAddedToUser_thenUserIsSetInPost() {
    User user = getUser("test@mail.com");

    Post post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    user.addPost(post);
    session.persist(user);
    session.flush();

    var retrievedPost = session.get(Post.class, post.getId());

    assertNotNull(retrievedPost);
    assertEquals(user.getId(), retrievedPost.getUser().getId());

  }

  @Test
  void whenCreateFriendship_thenItIsPersistedCorrectly() {
    User user1 = getUser("test1@mail.com");
    User user2 = getUser("test2@mail.com");
    session.persist(user1);
    session.persist(user2);

    Friendship friendship = Friendship.builder()
        .user(user1)
        .friend(user2)
        .status(Status.PENDING)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
    friendship.setUser(user1);
    friendship.setFriend(user2);

    session.persist(friendship);
    session.flush();

    Friendship retrievedFriendship = session.get(Friendship.class, friendship.getId());

    assertNotNull(retrievedFriendship);
    assertEquals(user1, retrievedFriendship.getUser());
    assertEquals(user2, retrievedFriendship.getFriend());
    assertEquals(Status.PENDING, retrievedFriendship.getStatus());
    assertTrue(user1.getInitiatedFriendships().contains(friendship));
    assertTrue(user2.getFriendshipsAsFriend().contains(friendship));
  }

  @Test
  void whenRetrieveNonExistentUser_thenShouldReturnNull() {
    User user = session.get(User.class, UUID.randomUUID());

    assertNull(user);
  }

  private static User getUser(String email) {
    return User.builder()
        .password("testPass")
        .firstName("TestName")
        .lastName("TestLast")
        .email(email)
        .role(Role.USER)
        .birthDate(LocalDate.of(1995, 5, 15))
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }

  private static Post getPost(User user) {
    return Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .updatedAt(Instant.now().truncatedTo(ChronoUnit.MICROS))
        .build();
  }

}
