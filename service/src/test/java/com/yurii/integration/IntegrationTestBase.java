package com.yurii.integration;

import com.yurii.util.HibernateTestUtil;
import com.yurii.util.TestDataImporter;
import java.lang.reflect.Proxy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class IntegrationTestBase {

  private static SessionFactory sessionFactory;
  public static Session session;
  protected Transaction transaction;


  @BeforeAll
  static void setUpSessionFactory() {
    sessionFactory = HibernateTestUtil.buildSessionFactory();
    session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
        new Class[]{Session.class},
        (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    TestDataImporter.importTestData(sessionFactory);
  }

  @BeforeEach
  void startTransaction() {
    session.beginTransaction();
  }

  @AfterEach
  void rollbackTransaction() {
    if (transaction != null && transaction.isActive()) {
      transaction.rollback();
    }
    if (session != null) {
      session.close();
    }
  }

//  @AfterAll
//  static void closeSessionFactory() {
//    if (sessionFactory != null) {
//      sessionFactory.close();
//    }
//  }
}
