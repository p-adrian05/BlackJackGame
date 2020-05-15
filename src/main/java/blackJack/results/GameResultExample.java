package blackJack.results;

import com.google.inject.Guice;
import com.google.inject.Injector;
import util.juice.PersistenceModule;

public class GameResultExample {

    public static void main(String[] args) {
        GameResultDao gameResultDao = GameResultDao.getInstance();
//        Injector injector = Guice.createInjector(new PersistenceModule("blackJackGame"));
//        GameResultDao gameResultDao = injector.getInstance(GameResultDao.class);
        GameResult gameResult = GameResult.builder()
                .username("adrian")
                .password("12334")
                .funds(360)
                .lostMoney(200)
                .wonMoney(20)
                .wonCount(2)
                .loseCount(2)
                .build();
        gameResultDao.persist(gameResult);
        System.out.println(gameResultDao.findAll());
    }

}
