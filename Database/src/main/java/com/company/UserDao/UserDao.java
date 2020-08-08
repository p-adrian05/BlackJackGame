package com.company.UserDao;


import com.company.domain.GameData;
import com.company.domain.User;

import java.util.Optional;

/**
 * DAO interface for the {@link User} entity.
 */
public interface UserDao extends GenericDao<User> {
    /**
     * Returns a {@link User} entity instance with the specified username from the
     * database. The method returns an empty {@link Optional} object when
     * the instance does not exists.
     *
     * @param username the String name to look for
     * @return an {@link Optional} object wrapping the {@link User} instance with
     * the specified username
     */
    Optional<User> findByUsername(String username);

    /**
     * Returns a {@link GameData} entity instance with the specified id from the
     * database.
     *
     * @param id the id to look for
     * @return Returns a {@link GameData} instance with
     * the specified primary key
     */
    GameData getGameDataById(Long id);
    /**
     * Updates the {@link GameData} entity instance in the database.
     *
     * @param entity the entity instance to be updated in the database
     */
    void updateGameData(GameData entity);
}
