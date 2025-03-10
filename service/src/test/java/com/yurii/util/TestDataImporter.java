package com.yurii.util;

import com.yurii.entity.Comment;
import com.yurii.entity.Friendship;
import com.yurii.entity.Like;
import com.yurii.entity.Post;
import com.yurii.entity.Role;
import com.yurii.entity.User;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {

  public void importTestData(SessionFactory sessionFactory) {
    @Cleanup Session session = sessionFactory.openSession();
    session.beginTransaction();

    User user1 = getUser("John", "Doe", "john@example.com");
    User user2 = getUser("Alice", "Smith", "alice@example.com");
    User user3 = getUser("Bob", "Brown", "bob@example.com");
    User user4 = getUser("Eve", "Johnson", "eve@example.com");
    User user5 = getUser("Charlie", "Davis", "charlie@example.com");

    session.persist(user1);
    session.persist(user2);
    session.persist(user3);
    session.persist(user4);
    session.persist(user5);

    Friendship friendship1 = getFriendship(user1, user2);
    Friendship friendship2 = getFriendship(user1, user3);
    Friendship friendship3 = getFriendship(user1, user4);

    Friendship friendship4 = getFriendship(user2, user3);
    Friendship friendship5 = getFriendship(user2, user5);

    session.persist(friendship1);
    session.persist(friendship2);
    session.persist(friendship3);
    session.persist(friendship4);
    session.persist(friendship5);

    Post post1 = getPost(user1);
    Post post2 = getPost(user1);
    Post post3 = getPost(user1);

    session.persist(post1);
    session.persist(post2);
    session.persist(post3);

    Comment comment1 = getComment(post1, user1);
    Comment comment2 = getComment(post1, user1);

    session.persist(comment1);
    session.persist(comment2);

    Like like1 = getLike(post1, user1);
    session.persist(like1);

    Post post4 = getPost(user2);
    session.persist(post4);

    Comment comment3 = getComment(post4, user2);
    session.persist(comment3);

    session.getTransaction().commit();
  }

  private static User getUser(String firstName, String lastName, String email) {
    return User.builder()
        .password("testPass")
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .role(Role.USER)
        .birthDate(LocalDate.of(1995, 5, 15))
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

  private static Comment getComment(Post post, User user) {
    return Comment.builder()
        .post(post)
        .user(user)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }

  private static Like getLike(Post post, User user) {
    return Like.builder()
        .post(post)
        .user(user)
        .createdAt(Instant.now())
        .build();
  }

  private static Friendship getFriendship(User user, User friend) {
    return Friendship.builder()
        .user(user)
        .friend(friend)
        .build();
  }


}
