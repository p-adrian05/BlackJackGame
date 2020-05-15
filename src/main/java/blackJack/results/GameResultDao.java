package blackJack.results;

import util.jpa.GenericJpaDao;

public class GameResultDao extends GenericJpaDao<GameResult> {

    public GameResultDao() {
        super(GameResult.class);
    }
}
