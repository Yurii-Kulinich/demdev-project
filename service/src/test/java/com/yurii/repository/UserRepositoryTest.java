package com.yurii.repository;


import com.yurii.entity.Role;
import com.yurii.entity.User;
import com.yurii.integration.IntegrationTestBase;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserRepositoryTest extends IntegrationTestBase {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private EntityManager entityManager;

  @Test
  void save_shouldPersistUser() {
    User user = getUser("john@example.com");
    userRepository.save(user);
    entityManager.flush();
    entityManager.clear();

    Optional<User> foundUser = userRepository.findById(user.getId());

    Assertions.assertTrue(foundUser.isPresent());
    Assertions.assertEquals("john@example.com", foundUser.get().getEmail());
  }

  @Test
  void delete_shouldRemoveUser() {
    User user = getUser("john@example.com");
    userRepository.save(user);
    entityManager.flush();
    userRepository.delete(user);
    entityManager.flush();

    Optional<User> foundUser = userRepository.findById(user.getId());

    Assertions.assertTrue(foundUser.isEmpty());
  }

  @Test
  void update_shouldModifyExistingUser() {
    User user = getUser("john@example.com");
    userRepository.save(user);
    entityManager.flush();
    user.setFirstName("UpdatedUser");
    userRepository.update(user);
    entityManager.flush();

    Optional<User> foundUser = userRepository.findById(user.getId());

    Assertions.assertTrue(foundUser.isPresent());
    Assertions.assertEquals("UpdatedUser", foundUser.get().getFirstName());
  }

  @Test
  void findById_shouldReturnExistingUser() {
    User user = getUser("john@example.com");
    var savedUser = userRepository.save(user);
    entityManager.flush();

    Optional<User> foundUser = userRepository.findById(savedUser.getId());

    Assertions.assertTrue(foundUser.isPresent());
    Assertions.assertEquals("john@example.com", foundUser.get().getEmail());
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
