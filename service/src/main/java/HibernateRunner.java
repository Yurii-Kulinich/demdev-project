import entity.Comment;
import entity.Friendship;
import entity.Like;
import entity.Post;
import entity.Role;
import entity.Status;
import entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {

  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

    configuration.configure();

    try (SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession()) {

      session.beginTransaction();

      User user = User.builder()
          .id(UUID.randomUUID())
          .userName("johndoe")
          .password("hashedpassword123")
          .firstName("John")
          .lastName("Doe")
          .email("john.doe@example.com")
          .role(Role.USER)
          .birthDate(LocalDate.of(1995, 5, 15))
          .profilePicture(null)
          .createdOn(LocalDateTime.now())
          .updatedOn(LocalDateTime.now())
          .build();

      session.persist(user);

      Post post = Post.builder()
          .id(UUID.randomUUID())
          .userId(user.getId()) // Use the same user's ID
          .title("My First Post")
          .text("Hello, this is my first post!")
          .createdOn(LocalDateTime.now())
          .updatedOn(LocalDateTime.now())
          .build();

      session.persist(post);

      Like like = Like.builder()
          .id(UUID.randomUUID())
          .userId(user.getId())
          .postId(post.getId())
          .createdOn(LocalDateTime.now())
          .build();

      session.persist(like);

      Friendship friendship = Friendship.builder()
          .id(UUID.randomUUID())
          .user1Id(user.getId())
          .user2Id(UUID.randomUUID()) // Another random user
          .status(Status.PENDING)
          .createdOn(LocalDateTime.now())
          .updatedOn(LocalDateTime.now())
          .build();

      session.persist(friendship);

      Comment comment = Comment.builder()
          .id(UUID.randomUUID())
          .postId(post.getId())
          .userId(user.getId())
          .text("This is my first comment!")
          .createdOn(LocalDateTime.now())
          .updatedOn(LocalDateTime.now())
          .build();

      session.persist(comment);

      session.getTransaction().commit();
    }

  }

}
