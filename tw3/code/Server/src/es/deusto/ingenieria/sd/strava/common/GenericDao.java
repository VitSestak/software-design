package es.deusto.ingenieria.sd.strava.common;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface GenericDao<T> {

    T find(UUID id);

    List<T> findAll();

    void persist(T entity);

    void persist(Collection<T> entities);

    T update (T entity);

    void remove(T entity);

    boolean exists(UUID id);
}
