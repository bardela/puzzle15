package com.ardela.puzzle15.repository.base;

import com.ardela.puzzle15.model.base.EntityWithId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class InMemoryRepository<T extends EntityWithId> implements Repository<T> {
  protected final Map<Integer, T> collection = new ConcurrentHashMap<>();
  protected final AtomicInteger count = new AtomicInteger();

  @Override
  public int count() {
    return collection.size();
  }

  @Override
  public T findById(Integer id) {
    return collection.getOrDefault(id, null);
  }

  @Override
  public List<T> findAll() {
    return new ArrayList<>(collection.values());
  }

  @Override
  public T save(T origin) {
    T dest = copyData(origin);
    if (dest.getId() == null) {
      dest.setId(count.incrementAndGet());
    }
    collection.put(dest.getId(), dest);
    return dest;
  }

  protected abstract T copyData(T origin);
}