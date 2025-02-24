package com.yurii.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.yurii.integration.IntegrationTestBase;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;

class PostTest extends IntegrationTestBase {

  @Test
  void whenPersistValidPost_thenShouldBeRetrievableFromDatabase() {
    var user = getUser();
    var post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    session.persist(user);
    session.persist(post);
    session.flush();

    var result = session.get(Post.class, post.getId());

    assertNotNull(result);
    assertEquals(post.getId(), result.getId());
    assertEquals(post.getTitle(), result.getTitle());
    assertEquals(post.getText(), result.getText());
    assertEquals(post.getCreatedAt(), result.getCreatedAt());
    assertEquals(post.getUpdatedAt(), result.getUpdatedAt());

  }

  @Test
  void whenPersistInvalidPost_thenShouldThrowException() {
    var user = getUser();
    var post = Post.builder()
        .user(user)
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    session.persist(user);
    session.persist(post);

    Exception exception = assertThrows(ConstraintViolationException.class, () -> {
      session.flush();
    });
    assertTrue(exception.getMessage().contains("null value in column \"title\" of relation \"post\" violates not-null constraint"));

  }

  @Test
  void whenUpdatePost_thenShouldBeUpdated() {
    var user = getUser();
    var post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    session.persist(user);
    session.persist(post);
    session.flush();

    post.setText("UpdatedText");
    post.setTitle("UpdatedTitle");

    session.merge(post);
    session.flush();

    var result = session.get(Post.class, post.getId());

    assertNotNull(result);
    assertEquals(post.getId(), result.getId());
    assertEquals("UpdatedTitle", result.getTitle());
    assertEquals("UpdatedText", result.getText());

  }

  @Test
  void whenDeletePost_thenShouldNotExist() {

    var user = getUser();
    var post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    session.persist(post);
    session.flush();

    var resultAfterPersist = session.get(Post.class, post.getId());
    assertNotNull(resultAfterPersist);

    session.remove(post);
    session.flush();

    var resultAfterDelete = session.get(Post.class, post.getId());
    assertNull(resultAfterDelete);
  }

  @Test
  void whenPostIsPersisted_thenUserIsPersistedToo(){
    var user = getUser();
    var post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    session.persist(post);
    session.flush();

    var retrievedUser = session.get(User.class, user.getId());
    assertEquals(user.getId(), retrievedUser.getId());

  }

  @Test
  void whenAddLikeToPost_thenPostShouldBeAddedToLike() {
    var user = getUser();

    var post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    Like like = Like.builder()
          .user(user)
          .post(post)
          .createdAt(Instant.now())
          .build();

    post.addLike(like);
    session.persist(post);

    session.flush();

    var retrievedPost = session.get(Post.class, post.getId());
    var retrievedLike = session.get(Like.class, like.getId());

    assertNotNull(retrievedPost);
    assertNotNull(retrievedLike);

    assertTrue(post.getLikes().contains(like));
    assertEquals(retrievedPost, retrievedLike.getPost());

  }

  @Test
  void whenAddCommentToPost_thenPostShouldBeAddedToComment() {
    var user = getUser();

    var post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    Comment comment = Comment.builder()
          .post(post)
          .user(user)
          .text("testComment")
          .createdAt(Instant.now())
          .updatedAt(Instant.now())
          .build();

    post.addComment(comment);
    session.persist(post);

    session.flush();

    var retrievedPost = session.get(Post.class, post.getId());
    var retrievedComment = session.get(Comment.class, comment.getId());

    assertNotNull(retrievedPost);
    assertNotNull(retrievedComment);

    assertTrue(post.getComments().contains(comment));
    assertEquals(retrievedPost, retrievedComment.getPost());

  }

  @Test
  void whenPostIsRemoved_thenLikesAreRemoved() {
    var user = getUser();

    var post = Post.builder()
        .user(user)
        .title("testPost")
        .text("testText")
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    Like like = Like.builder()
        .user(user)
        .post(post)
        .createdAt(Instant.now())
        .build();

    post.addLike(like);
    session.persist(post);
    session.flush();

    session.remove(post);
    session.flush();

    var retrievedLike = session.get(Like.class, like.getId());
    assertNull(retrievedLike);

  }

  private static User getUser() {

    return User.builder()
        .password("testPass")
        .firstName("TestName")
        .lastName("TestLast")
        .email("test@example.com")
        .role(Role.USER)
        .birthDate(LocalDate.of(1995, 5, 15))
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }

}
