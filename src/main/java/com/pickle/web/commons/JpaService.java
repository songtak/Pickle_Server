package com.pickle.web.commons;

import java.util.Optional;

public interface JpaService<T> {
    public Optional<T> findById(String id);
    public Iterable<T> findAll();
    public int count();
    public void delete(String id);
    public boolean exists(String id) ;
}
