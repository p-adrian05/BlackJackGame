package blackJack.results;

import org.checkerframework.checker.nullness.Opt;
import util.jpa.GenericJpaDao;

import javax.persistence.Persistence;
import javax.swing.text.html.Option;
import java.util.Optional;

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
    public Optional<User> findbyUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
