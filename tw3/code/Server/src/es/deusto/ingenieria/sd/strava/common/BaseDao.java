package es.deusto.ingenieria.sd.strava.common;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class BaseDao<T> implements GenericDao<T> {

    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("stravaServer");
    protected static final EntityManager em = emf.createEntityManager();

    protected final Class<T> type;

    public BaseDao(Class<T> type) {
        this.type = type;
    }

    public T find(UUID id) {
        Objects.requireNonNull(id);
        var tx = em.getTransaction();

        T result;

        try {
            tx.begin();
            result = em.find(type, id);
            tx.commit();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        var tx = em.getTransaction();

        List<T> result;

        try {
            tx.begin();
            result = em.createQuery("SELECT c FROM " + type.getSimpleName() + " c", type).getResultList();
            tx.commit();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
        return result;
    }

    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        var tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        var tx = em.getTransaction();

        if (entities.isEmpty()) {
            return;
        }
        try {
            tx.begin();
            entities.forEach(this::persist);
            tx.commit();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public T update (T entity) {
        Objects.requireNonNull(entity);
        var tx = em.getTransaction();

        T result;

        try {
            tx.begin();
            result = em.merge(entity);
            tx.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new PersistenceException(e);
        }
        return result;
    }

    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        var tx = em.getTransaction();

        try (em) {
            tx.begin();
            final T toRemove = em.merge(entity);
            if (toRemove != null) {
                em.remove(entity);
            }
            tx.commit();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean exists(UUID id) {
        var tx = em.getTransaction();

        boolean result;
        try {
            tx.begin();
            result = id != null && em.find(type, id) != null;
            tx.commit();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
        return result;
    }
}
