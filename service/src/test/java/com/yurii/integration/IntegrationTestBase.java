//package com.yurii.integration;
//
//import com.yurii.config.ApplicationTestConfig;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationTestConfig.class)
//public abstract class IntegrationTestBase {
//
//  @Autowired
//  protected EntityManager em;
//
////  @Autowired
////  private SessionFactory sessionFactory;
////  @Autowired
////  public Session session;
////  protected Transaction transaction;
////
////  @BeforeEach
////  void startTransaction() {
////    session.beginTransaction();
////  }
////
////  @AfterEach
////  void rollbackTransaction() {
////    if (transaction != null && transaction.isActive()) {
////      transaction.rollback();
////    }
////    if (session != null) {
////      session.close();
////    }
////  }
//
//}
