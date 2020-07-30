package com.company.blackJack;

import com.company.domain.GameData;
import com.company.blackJack.card.Card;
import com.company.blackJack.card.CardApi;
import com.company.blackJack.card.Deck;
import com.company.blackJack.game.*;

import com.company.domain.User;
import com.company.UserDao.UserDao;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class collects all objects needed the game and provides game related functions.
 */
@Slf4j
@Service
@Getter
public class GameService {

    private final UserDao userDao;
    private Player player;
    private Person dealer;
    private final CardApi cardApi;
    private final GameUtils gameUtils;
    private Deck deck;
    @Setter
    private User user;

    @Autowired
    public GameService(UserDao userDao, CardApi cardApi, GameUtils gameUtils) {
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
        setPlayerFund(user.getGameData().getFunds());
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
        boolean pass1 = getPlayerCardsValue()>21;
        if(getPlayerSplitCardsValue()>0){
            boolean pass2 = getPlayerSplitCardsValue()>21;
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
        if(getPlayerSplitCardsValue()>0){
            boolean pass1 = getPlayerCardsValue()>21;
            boolean pass2 = getPlayerSplitCardsValue()>21;
            return !pass1 && pass2;
        }
        return false;
    }

    /**
     * Returns the final results of the game using {@link GameUtilsImpl#calculateResult(int, int, int)} function.
     *
     * @return an array of {@link Result} enum values
     */
    public Result[] getResults(){
        return gameUtils.calculateResult(getPlayerCardsValue(),
                getPlayerSplitCardsValue(),
                getDealerCardsValue());
    }

    /**
     * Returns the final prize of the player using {@link GameUtilsImpl#calculatePrizes(int, Result[])}.
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
        GameData gameData = user.getGameData();
        int[] prizes = getPrizes(getResults());
        int profit = gameUtils.calcProfit(prizes,player.getBet());
        gameData.setFunds(gameUtils.calculateFund(prizes,player.getFund().intValue()));
        player.getFund().set(gameData.getFunds());
        if(player.getBet()>gameData.getMaxBet()){
            gameData.setMaxBet(player.getBet());
        }
        if(profit<0){
            gameData.setLostMoney(gameData.getLostMoney() + profit);
            gameData.setLoseCount(gameData.getLoseCount() + 1);
        }else if(profit>0){
            gameData.setWonMoney(gameData.getWonMoney() + profit);
            gameData.setWonCount(gameData.getWonCount() + 1);
        }
        userDao.update(user);
    }
    /**
     * Returns the summary value of the {@link Player }{@link Card} objects list
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    public int getPlayerCardsValue(){
        return deck.calcCardsSumValue(player.getCards());
    }
    /**
     * Returns the summary value of the {@link Player } {@link Card} objects list,
     * which contains the split cards,
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    public int getPlayerSplitCardsValue(){
        return deck.calcCardsSumValue(player.getSplitCards());
    }
    /**
     * Returns the summary value of the {@link Dealer } {@link Card} objects list,
     * which contains the split cards,
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    public int getDealerCardsValue(){
        return deck.calcCardsSumValue(dealer.getCards());
    }
}
