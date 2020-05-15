package blackJack.results;

import util.jpa.GenericJpaDao;

import javax.persistence.Persistence;

public class GameResultDao extends GenericJpaDao<GameResult> {

    private GameResultDao() {
        super(GameResult.class);
    }
    private static GameResultDao instance;

    public static GameResultDao getInstance() {
        if (instance == null) {
            instance = new GameResultDao();
            instance.setEntityManager(Persistence.createEntityManagerFactory("blackJackGame").createEntityManager());
        }
        return instance;
    }
}
