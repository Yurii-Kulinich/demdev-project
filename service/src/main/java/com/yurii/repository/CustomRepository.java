package com.yurii.repository;

import java.io.Serializable;
import java.util.List;

public interface CustomRepository<K extends Serializable, E> {

  List<E> findAllByCriteria();
}
