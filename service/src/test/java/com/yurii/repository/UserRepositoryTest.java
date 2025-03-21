package com.yurii.repository;


import com.yurii.entity.Role;
import com.yurii.entity.User;
import com.yurii.integration.IntegrationTestBase;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRepositoryTest extends IntegrationTestBase {

  private UserRepository userRepository;

  @BeforeEach
  void init() {
    userRepository = new UserRepository(session);
  }

  @Test
  void save_shouldPersistUser() {
    User user = getUser("john@example.com");
    userRepository.save(user);
    session.flush();
    session.clear();

    Optional<User> foundUser = userRepository.findById(user.getId());

    Assertions.assertTrue(foundUser.isPresent());
    Assertions.assertEquals("john@example.com", foundUser.get().getEmail());
  }

  @Test
  void delete_shouldRemoveUser() {
    User user = getUser("john@example.com");
    userRepository.save(user);
    session.flush();
    userRepository.delete(user);
    session.flush();

    Optional<User> foundUser = userRepository.findById(user.getId());

    Assertions.assertTrue(foundUser.isEmpty());
  }

  @Test
  void update_shouldModifyExistingUser() {
    User user = getUser("john@example.com");
    userRepository.save(user);
    session.flush();
    user.setFirstName("UpdatedUser");
    userRepository.update(user);
    session.flush();

    Optional<User> foundUser = userRepository.findById(user.getId());

    Assertions.assertTrue(foundUser.isPresent());
    Assertions.assertEquals("UpdatedUser", foundUser.get().getFirstName());
  }

  @Test
  void findById_shouldReturnExistingUser() {
    User user = getUser("john@example.com");
    var savedUser = userRepository.save(user);
    session.flush();

    Optional<User> foundUser = userRepository.findById(savedUser.getId());

    Assertions.assertTrue(foundUser.isPresent());
    Assertions.assertEquals("john@example.com", foundUser.get().getEmail());
  }

  @Test
  void findAll_shouldReturnMultipleUsers() {
    List<User> users = userRepository.findAll();

    Assertions.assertFalse(users.isEmpty());
    Assertions.assertTrue(users.size() == 5);
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

}
