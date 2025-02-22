import entity.Comment;
import entity.Friendship;
import entity.Like;
import entity.Post;
import entity.Role;
import entity.Status;
import entity.User;
import java.time.Instant;
import java.time.LocalDate;
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
          .password("hashedpassword123")
          .firstName("John")
          .lastName("Doe")
          .email("john.doe@example.com")
          .role(Role.USER)
          .birthDate(LocalDate.of(1995, 5, 15))
          .profilePicture(null)
          .createdAt(Instant.now())
          .updatedAt(Instant.now())
          .build();

      User user1 = User.builder()
          .password("hashedpassword123")
          .firstName("John1")
          .lastName("Doe1")
          .email("john1.doe@example.com")
          .role(Role.USER)
          .birthDate(LocalDate.of(1995, 5, 15))
          .profilePicture(null)
          .createdAt(Instant.now())
          .updatedAt(Instant.now())
          .build();

      session.persist(user);
      session.persist(user1);

      Post post = Post.builder()
          .userId(user.getId())
          .title("My First Post")
          .text("Hello, this is my first post!")
          .createdAt(Instant.now())
          .updatedAt(Instant.now())
          .build();

      session.persist(post);

      Like like = Like.builder()
          .userId(user.getId())
          .postId(post.getId())
          .createdAt(Instant.now())
          .build();

      session.persist(like);

      Friendship friendship = Friendship.builder()
          .user1Id(user.getId())
          .user2Id(user1.getId())
          .status(Status.PENDING)
          .createdAt(Instant.now())
          .updatedAt(Instant.now())
          .build();

      session.persist(friendship);

      Comment comment = Comment.builder()
          .postId(post.getId())
          .userId(user.getId())
          .text("This is my first comment!")
          .createdAt(Instant.now())
          .updatedAt(Instant.now())
          .build();

      session.persist(comment);

      session.getTransaction().commit();
    }

  }

}
