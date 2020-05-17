package blackJack.results;

import org.checkerframework.checker.nullness.Opt;
import util.jpa.GenericJpaDao;

import javax.persistence.Persistence;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * DAO class for the {@link User} entity.
 */
public class UserDao extends GenericJpaDao<User> {

    private UserDao() {
        super(User.class);
    }
    private static UserDao instance;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("blackJackGame").createEntityManager());
        }
        return instance;
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
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
