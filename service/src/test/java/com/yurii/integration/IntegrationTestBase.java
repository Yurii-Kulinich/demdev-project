package com.yurii.integration;

import com.yurii.util.HibernateTestUtil;
import com.yurii.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class IntegrationTestBase {

  private static SessionFactory sessionFactory;
  protected Session session;
  protected Transaction transaction;


  @BeforeAll
  static void setUpSessionFactory() {
    sessionFactory = HibernateTestUtil.buildSessionFactory();
    TestDataImporter.importTestData(sessionFactory);
  }

  @BeforeEach
  void startTransaction() {
    session = sessionFactory.openSession();
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
