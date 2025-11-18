package com.yurii.repository;

import static com.yurii.entity.Role.USER;

import com.yurii.entity.Post;
import com.yurii.entity.User;
import com.yurii.integration.IntegrationTestBase;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class PostRepositoryTest extends IntegrationTestBase {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private EntityManager entityManager;
  private User user;

  @BeforeEach
  void init() {
    user = getUser("user@example.com");
    entityManager.persist(user);
    entityManager.flush();
  }

  @Test
  void save_shouldPersistPost() {
    Post post = getPost(user, "Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    entityManager.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isPresent());
    Assertions.assertEquals("Post Title", foundPost.get().getTitle());
  }

  @Test
  void delete_shouldRemovePost() {
    Post post = getPost(user, "Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    entityManager.flush();
    postRepository.delete(post);
    entityManager.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isEmpty());
  }

  @Test
  void update_shouldModifyExistingPost() {
    Post post = getPost(user, "Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    entityManager.flush();
    post.setTitle("Updated Post Title");
    entityManager.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isPresent());
    Assertions.assertEquals("Updated Post Title", foundPost.get().getTitle());
  }

  @Test
  void findById_shouldReturnExistingPost() {
    Post post = getPost(user, "Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    entityManager.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isPresent());
    Assertions.assertEquals("Post Title", foundPost.get().getTitle());
  }

  private static User getUser(String email) {
    return User.builder()
        .password("testPass")
        .firstName("TestName")
        .lastName("TestLast")
        .email(email)
        .role(USER)
        .birthDate(LocalDate.of(1995, 5, 15))
        .build();
  }

  private static Post getPost(User user, String title, String text) {
    return Post.builder()
        .user(user)
        .title(title)
        .text(text)
        .postPicture("pictureUrl1")
        .user(null)
        .build();
  }

}
