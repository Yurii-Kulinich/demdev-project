package com.yurii.repository;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

//@AllArgsConstructor
@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E> implements Repository<K, E> {

  private final Class<E> clazz;
  private final EntityManager em;

  @Override
  public E save(E entity) {
    em.persist(entity);
    return entity;
  }

  @Override
  public void delete(E entity) {
    if (entity != null) {
      em.remove(entity);
    }
  }

  @Override
  public void update(E entity) {
    em.merge(entity);
  }

  @Override
  public Optional<E> findById(K id) {
    return Optional.ofNullable(em.find(clazz, id));
  }

  @Override
  public List<E> findAll() {
    var criteria = em.getCriteriaBuilder().createQuery(clazz);
    criteria.from(clazz);
    return em.createQuery(criteria)
        .getResultList();
  }
}
