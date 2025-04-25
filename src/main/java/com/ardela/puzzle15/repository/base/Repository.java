package com.ardela.puzzle15.repository.base;

import com.ardela.puzzle15.model.base.EntityWithId;

import java.util.List;

public interface Repository<T extends EntityWithId> {
  int count();
  T findById(Integer id);
  List<T> findAll();
  T save(T origin);
}