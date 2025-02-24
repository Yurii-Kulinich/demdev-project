package com.yurii.util;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

  private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

  public static SessionFactory getSessionFactory() {
    return SESSION_FACTORY;
  }

  private static SessionFactory buildSessionFactory() {
    Configuration configuration = getConfiguration();
    configuration.configure();
    return configuration.buildSessionFactory();
  }

  public static Configuration getConfiguration() {
    Configuration configuration = new Configuration();
    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    return configuration;
  }
}
