package com.company.blackJack;

import com.company.domain.GameData;
import com.company.blackJack.card.CardApi;
import com.company.blackJack.card.Deck;
import com.company.blackJack.game.*;

import com.company.domain.User;
import com.company.UserDao.UserDao;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Class collects all objects needed the game and provides game related functions.
 */
@Slf4j
@Service
@Getter
public class GameServiceImpl implements GameService {

    @Getter(AccessLevel.NONE)
    private final UserDao userDao;
    private Player player;
    private Person dealer;
    @Getter(AccessLevel.NONE)
    private final CardApi cardApi;
    @Getter(AccessLevel.NONE)
    private final GameUtils gameUtils;
    private Deck deck;
    @Setter
    private User user;

    @Autowired
    public GameServiceImpl(UserDao userDao, CardApi cardApi, GameUtils gameUtils) {
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

    @Override
    public void setPlayerFund(int fund){
        player.getFund().set(fund);
    }
    @Override
    public void resetGame(){
        player = new PlayerImpl();
        dealer = new Dealer();
        setPlayerFund(user.getGameData().getFunds());
        if(deck!= null && deck.getDeckCards().size()<20){
            this.deck = cardApi.getDeck().get();
        }
    }
    @Override
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
    @Override
    public boolean isSplitGameOver(){
        if(getPlayerSplitCardsValue()>0){
            boolean pass1 = getPlayerCardsValue()>21;
            boolean pass2 = getPlayerSplitCardsValue()>21;
            return !pass1 && pass2;
        }
        return false;
    }
    @Override
    public Result[] getResults(){
        return gameUtils.calculateResult(getPlayerCardsValue(),
                getPlayerSplitCardsValue(),
                getDealerCardsValue());
    }
    @Override
    public int[] getPrizes(Result[] results){
        return gameUtils.calculatePrizes(player.getBet(),results);
    }
    @Override
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
    @Override
    public int getPlayerCardsValue(){
        return deck.calcCardsSumValue(player.getCards());
    }
    @Override
    public int getPlayerSplitCardsValue(){
        return deck.calcCardsSumValue(player.getSplitCards());
    }
    @Override
    public int getDealerCardsValue(){
        return deck.calcCardsSumValue(dealer.getCards());
    }
}
