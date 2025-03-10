package com.yurii.repository;

import static com.yurii.entity.Role.*;

import com.yurii.dao.PostRepository;
import com.yurii.entity.Post;
import com.yurii.entity.User;
import com.yurii.integration.IntegrationTestBase;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostRepositoryTest extends IntegrationTestBase {

  private PostRepository postRepository;
  private User user;

  @BeforeEach
  void init() {
    postRepository = new PostRepository(session);
    user = getUser("user@example.com");
    session.persist(user);
    session.flush();
  }

  @Test
  void save_shouldPersistPost() {
    Post post = getPost(user, "Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    session.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isPresent());
    Assertions.assertEquals("Post Title", foundPost.get().getTitle());
  }

  @Test
  void delete_shouldRemovePost() {
    Post post = getPost(user, "Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    session.flush();
    postRepository.delete(post.getId());
    session.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isEmpty());
  }

  @Test
  void update_shouldModifyExistingPost() {
    Post post = getPost(user,"Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    session.flush();
    post.setTitle("Updated Post Title");
    postRepository.update(post);
    session.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isPresent());
    Assertions.assertEquals("Updated Post Title", foundPost.get().getTitle());
  }

  @Test
  void findById_shouldReturnExistingPost() {
    Post post = getPost(user, "Post Title", "This is the text of the post.");
    post.setUser(user);
    postRepository.save(post);
    session.flush();

    Optional<Post> foundPost = postRepository.findById(post.getId());

    Assertions.assertTrue(foundPost.isPresent());
    Assertions.assertEquals("Post Title", foundPost.get().getTitle());
  }

  @Test
  void findAll_shouldReturnMultiplePosts() {
    List<Post> posts = postRepository.findAll();

    Assertions.assertFalse(posts.isEmpty());
    Assertions.assertEquals(4, posts.size());
  }

  private static User getUser(String email) {
    return User.builder()
        .password("testPass")
        .firstName("TestName")
        .lastName("TestLast")
        .email(email)
        .role(USER)
        .birthDate(LocalDate.of(1995, 5, 15))
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }

  private static Post getPost(User user, String title, String text) {
    return Post.builder()
        .user(user)
        .title(title)
        .text(text)
        .postPicture("pictureUrl")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .user(null) // Set the user later
        .build();
  }

}
