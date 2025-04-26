package com.ardela.puzzle15.repository.base;

import com.ardela.puzzle15.model.base.EntityWithId;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends EntityWithId> {
  int count();
  Optional<T> findById(Integer id);
  List<T> findAll();
  T save(T origin);
}