package com.yurii;

import com.yurii.entity.Role;
import com.yurii.entity.User;
import com.yurii.repository.UserRepository;
import java.lang.reflect.Proxy;
import java.time.LocalDate;
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

    try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
      var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
          new Class[]{Session.class},
          (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

      session.beginTransaction();
      var userRepository = new UserRepository(session);
      User user1 = getUser("John", "Doe", "john@example.com");
      userRepository.save(user1);
      session.flush();
      userRepository.delete(user1);
      session.getTransaction().commit();
    }

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

}
