package blackJack.results;

public class GameResultExample {

    public static void main(String[] args) {
        UserDao userDao = UserDao.getInstance();
//        Injector injector = Guice.createInjector(new PersistenceModule("blackJackGame"));
//        GameResultDao gameResultDao = injector.getInstance(GameResultDao.class);
        User user = User.builder()
                .username("adrian")
                .password("12334")
                .funds(360)
                .lostMoney(200)
                .wonMoney(20)
                .wonCount(2)
                .loseCount(2)
                .build();
        userDao.persist(user);
        System.out.println(userDao.findAll());
    }

}
