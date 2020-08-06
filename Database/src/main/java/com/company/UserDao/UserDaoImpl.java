package com.company.UserDao;

import com.company.domain.GameData;
import com.company.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<GameData> getGameDataById(Long id) {
        try {
            return Optional.of(entityManager.createQuery("SELECT g FROM GameData g WHERE g.id = :id", GameData.class)
                    .setParameter("id", id).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Long persist(User entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class,id));
    }


    @Override
    public Optional<List<User>> findAll() {
        return Optional.ofNullable(entityManager
                .createQuery("SELECT u FROM User u",User.class).getResultList());
    }

    @Override
    @Transactional
    public void update(User entity) {
        entityManager.merge(entity);
    }
    @Override
    @Transactional
    public void updateGameData(GameData entity) {
        entityManager.merge(entity);
    }



    @Override
    @Transactional
    public void remove(User entity) {
        entityManager.remove(entity);
    }
}
