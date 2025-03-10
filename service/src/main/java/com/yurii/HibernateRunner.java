package com.yurii;

import com.yurii.dao.UserRepository;
import java.lang.reflect.Proxy;
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
//      userRepository.findAll().forEach(System.out::println);
      userRepository.delete(UUID.fromString("e48f4db2-6ac6-42f5-97b3-941dacbb9026"));
      session.getTransaction().commit();
    }

  }

}
