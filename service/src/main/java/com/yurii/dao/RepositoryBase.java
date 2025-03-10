package com.yurii.dao;

import jakarta.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E> implements Repository<K, E> {

  private final Class<E> clazz;
  private final EntityManager entityManager;

  @Override
  public E save(E entity) {
    entityManager.persist(entity);
    return entity;
  }

  @Override
  public void delete(K id) {
    E entity = entityManager.find(clazz, id);
    if (entity != null) {
      entityManager.remove(entityManager.find(clazz, id));
    }

  }

  @Override
  public void update(E entity) {
    entityManager.merge(entity);
  }

  @Override
  public Optional<E> findById(K id) {
    return Optional.ofNullable(entityManager.find(clazz, id));
  }

  @Override
  public List<E> findAll() {
    var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
    criteria.from(clazz);
    return entityManager.createQuery(criteria)
        .getResultList();
  }
}
