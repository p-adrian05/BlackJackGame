package blackJack.model;

import blackJack.model.card.CardApi;
import blackJack.model.card.Deck;
import blackJack.model.card.FranceCardApi;
import blackJack.model.game.*;
import blackJack.results.User;
import blackJack.results.UserDao;

import javax.inject.Inject;

/**
 * Class collects all objects needed the game and provides game related functions.
 */
public class Model {

    /**
     * Make a {@code static final Model} object instance as singleton pattern requires.
     */
    private static final Model model = new Model();

    private UserDao userDao = UserDao.getInstance();


    private Player player;
    private Person dealer;
    private CardApi cardApi;
    private GameUtils gameUtils;
    private Deck deck;
    private User user;

    private Model() {
        this.player = new Player();
        this.dealer = new Dealer();
        this.cardApi = new FranceCardApi();
        this.gameUtils = new GameUtils();
        this.deck = cardApi.getDeck();
    }

    public Player getPlayer() {
        return player;
    }

    public Person getDealer() {
        return dealer;
    }

    public Deck getDeck() {
        return deck;
    }

    public static Model getInstance(){
        return model;
    }

    public GameUtils getGameUtils() {
        return gameUtils;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    /**
     * Makes new instances of {@link Player}, {@link Dealer} and {@link Deck} objects
     * for making a new game.
     */
    public void resetGame(){
        this.player = new Player();
        this.dealer = new Dealer();
        if(deck.getDeckCards().size()<20){
            this.deck = cardApi.getDeck();
        }
    }
    /**
     * Returns whether the actual game is over checking the scores.
     *
     * @return {@code true} if the {@link Player} score(s) pass a specified value, {@code false} otherwise
     */
    public boolean isGameOver(){
        boolean pass1 = player.getCardsSumValues()>21;
        if(player.getCardsSumValuesSplit()>0){
            boolean pass2 = player.getCardsSumValuesSplit()>21;
            return pass1 && pass2;
        }
        else{
            return pass1;
        }
    }

    /**
     * Returns whether the actual game is over in {@link Player#isEnableSplitCards()} case.
     *
     * @return {@code true} if the {@link Player} first score not pass 21 value and the second pass 21, {@code false} otherwise
     */
    public boolean isSplitGameOver(){
        if(player.getCardsSumValuesSplit()>0){
            boolean pass1 = player.getCardsSumValues()>21;
            boolean pass2 = player.getCardsSumValuesSplit()>21;
            return !pass1 && pass2;
        }
        return false;
    }

    /**
     * Returns the final results of the game using {@link GameUtils#calculateResult(int, int, int)} function.
     *
     * @return an array of {@link Result} enum values
     */
    public Result[] getResults(){
        return gameUtils.calculateResult(player.getCardsSumValues(),
                player.getCardsSumValuesSplit(),
                dealer.getCardsSumValues());
    }

    /**
     * Returns the final prize of the player using {@link GameUtils#calculatePrizes(int, Result[])}.
     *
     * @param results an array of {@link Result} enum values
     * @return an array of int prizes
     */
    public int[] getPrizes(Result[] results){
        return gameUtils.calculatePrizes(player.getBet(),results);
    }

    /**
     * Sets user entity's attributes and save it to the database.
     */
    public void saveUser(){
        int[] prizes = getPrizes(getResults());
        int profit = gameUtils.calcProfit(prizes,player.getBet());
        user.setFunds(gameUtils.calculateFund(prizes,player.getFund()));
        if(player.getBet()>user.getMaxBet()){
            user.setMaxBet(player.getBet());
        }
        if(profit<0){
            user.setLostMoney(user.getLostMoney() + profit);
            user.setLoseCount(user.getLoseCount() + 1);
        }else if(profit>0){
            user.setWonMoney(user.getWonMoney() + profit);
            user.setWonCount(user.getWonCount() + 1);
        }
    }
}
