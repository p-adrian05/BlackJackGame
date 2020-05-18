package blackJack.results;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;
import java.util.Optional;

/**
 * DAO class for the {@link User} entity.
 */
public class UserDao extends GenericJpaDao<User> {
    public UserDao() {
        super(User.class);
    }
    /**
     * Returns a {@link User} entity instance with the specified username from the
     * database. The method returns an empty {@link Optional} object when
     * the instance does not exists.
     *
     * @param username the String name to look for
     * @return an {@link Optional} object wrapping the {@link User} instance with
     * the specified primary key
     */
    @Transactional
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
