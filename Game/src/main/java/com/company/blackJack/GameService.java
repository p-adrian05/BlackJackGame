package com.company.blackJack;

import com.company.blackJack.card.CardApi;
import com.company.blackJack.card.Deck;
import com.company.blackJack.card.FranceCardApi;
import com.company.blackJack.game.*;

import com.company.UserDao.User;
import com.company.UserDao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class collects all objects needed the game and provides game related functions.
 */
@Slf4j
@Service
public class GameService {
    
    private final UserDao userDao;
    private Player player;
    private Person dealer;
    private final CardApi cardApi;
    private final GameUtils gameUtils;
    private Deck deck;
    private User user;

    @Autowired
    public GameService(UserDao userDao,CardApi cardApi,GameUtils gameUtils) {
        this.userDao = userDao;
        this.gameUtils = gameUtils;
        this.cardApi = cardApi;
        this.player = new PlayerImpl();
        this.dealer = new Dealer();
        if(cardApi.getDeck().isPresent()){
            this.deck = cardApi.getDeck().get();
        }else{
            log.error("Failed to load cards data from json file.");
        }
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

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void setPlayerFund(int fund){
        player.getFund().set(fund);
    }

    /**
     * Makes new instances of {@link PlayerImpl}, {@link Dealer} and {@link Deck} objects
     * for making a new game.
     */
    public void resetGame(){
        player = new PlayerImpl();
        dealer = new Dealer();
        setPlayerFund(user.getFunds());
        if(deck!= null && deck.getDeckCards().size()<20){
            this.deck = cardApi.getDeck().get();
        }
    }
    /**
     * Returns whether the actual game is over checking the scores.
     *
     * @return {@code true} if the {@link PlayerImpl} score(s) pass a specified value, {@code false} otherwise
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
     * Returns whether the actual game is over in {@link PlayerImpl#isEnableSplitCards()} case.
     *
     * @return {@code true} if the {@link PlayerImpl} first score not pass 21 value and the second pass 21, {@code false} otherwise
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
        user.setFunds(gameUtils.calculateFund(prizes,player.getFund().intValue()));
        player.getFund().set(user.getFunds());
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
        userDao.update(user);
    }
}
