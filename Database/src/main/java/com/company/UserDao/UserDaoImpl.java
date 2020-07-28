package com.company.UserDao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public long persist(User entity) {
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
    public void update(User entity) {
        entityManager.createQuery("UPDATE User u SET u.funds = :funds WHERE u.id = :id")
                .setParameter("funds",entity.getFunds())
                .setParameter("id",entity.getId())
                .executeUpdate();
    }

    @Override
    public void remove(User entity) {
        entityManager.remove(entity);
    }

    public void clear(){
        entityManager.clear();
    }
}
