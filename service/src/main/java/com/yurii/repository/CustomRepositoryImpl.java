package com.yurii.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class CustomRepositoryImpl<K extends Serializable, E> implements
    CustomRepository<K, E> {

  protected final EntityManager em;
  private final Class<E> clazz;

  protected CustomRepositoryImpl(Class<E> clazz, EntityManager em) {
    this.clazz = clazz;
    this.em = em;
  }

  @Override
  public List<E> findAllByCriteria() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<E> query = cb.createQuery(clazz);
    Root<E> root = query.from(clazz);
    query.select(root);
    return em.createQuery(query).getResultList();
  }
}
