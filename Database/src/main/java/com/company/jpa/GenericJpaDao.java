package com.company.jpa;

import java.util.List;
import java.util.Optional;

/**
 * Generic JPA DAO interface that provides JPA support for the entity class
 * specified.
 *
 * @param <T> the type of the entity class
 */
public interface GenericJpaDao<T> {

    /**
     * Persists the specified entity instance in the database.
     *
     * @param entity the entity instance to be persisted in the database
     */
     Long persist(T entity);

    /**
     * Returns the entity instance with the specified primary key from the
     * database. The method returns an empty {@link Optional} object when
     * the instance does not exists.
     *
     * @param id the primary key to look for
     * @return an {@link Optional} object wrapping the entity instance with
     * the specified primary key
     */
     Optional<T> findById(Long id);

    /**
     * Returns a {@link Optional} list of all instances of the entity class from the database.
     *
     * @return an {@link Optional} object wrapping the list of all instances of the entity class from the database
     */
     Optional<List<T>> findAll();
    /**
     * Removes the specified entity instance from the database.
     *
     * @param entity the entity instance to be removed from the database
     */
     void remove(T entity);
    /**
     * Updates the specified entity instance in the database.
     *
     * @param entity the entity instance to be updated in the database
     */
     void update(T entity);

}