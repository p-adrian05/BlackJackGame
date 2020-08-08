package com.company.blackJack;

import com.company.blackJack.card.Card;
import com.company.domain.GameData;
import com.company.blackJack.card.CardApi;
import com.company.blackJack.card.Deck;
import com.company.blackJack.game.*;

import com.company.UserDao.UserDao;
import javafx.beans.property.IntegerProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
/**
 * Class implementation of {@link GameService} interface
 */
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private ApplicationContext appContext;

    private final CardApi cardApi;
    private final GameUtils gameUtils;
    private final UserDao userDao;
    private Deck deck;
    private Player player;
    private Person dealer;

    @Setter
    @Getter
    private Long gameDataId;
    @Setter
    @Getter
    private String username;

    @Autowired
    public GameServiceImpl(UserDao userDao, CardApi cardApi, GameUtils gameUtils) {
        this.userDao = userDao;
        this.gameUtils = gameUtils;
        this.cardApi = cardApi;
    }

    @PostConstruct
    public void initDeck(){
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
    public Card loadCardToPerson(Class<? extends Person> personClass){
        if(deck.getDeckCards().size()<10){
            initDeck();
        }
        if(deck!=null){
            Card card = deck.getCard();
            if(personClass.getName().equals(Player.class.getName())){
                player.addCard(card);
            }else{
                dealer.addCard(card);
            }
            return card;
        }
        return null;
    }
    @Override
    public void resetGame(){
        this.player = appContext.getBean(Player.class);
        this.dealer = appContext.getBean("dealer",Person.class);
        setPlayerFund(userDao.getGameDataById(gameDataId).getFunds());
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
    public String getResultsString(){
        return gameUtils.madeStringResult(getResults());
    }
    @Override
    public int[] getPrizes(){
        return gameUtils.calculatePrizes(player.getBet(),getResults());
    }
    private Result[] getResults(){
        return  gameUtils.calculateResult(getPlayerCardsValue(),
                getPlayerSplitCardsValue(),
                getDealerCardsValue());
    }
    @Override
    public void saveUser(){
        GameData gameData = userDao.getGameDataById(gameDataId);
        int[] prizes = getPrizes();
        int profit = gameUtils.calcProfit(prizes,player.getBet());
        gameData.setFunds(gameUtils.calculateFund(prizes,player.getFund().intValue()));
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
        userDao.updateGameData(gameData);
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
    @Override
    public boolean enablePlayerSplitCards(){
        if(player.isEnableSplitCards() && addPlayerBet(player.getBet())){
            player.madeSplitCards();
            return true;
        }
        return false;
    }
    @Override
    public boolean addPlayerBet(int bet){
        if(gameUtils.validateBet(bet,player.getFund().getValue())){
            player.addBetFromFund(bet);
            return true;
        }
        return false;
    }
    @Override
    public void addSecondHandToPlayer(){
        player.madeSecondHand();
    }
    @Override
    public IntegerProperty getPlayerFund(){
        return player.getFund();
    }
    @Override
    public boolean isDoubleEnable(){
        return player.getCards().size() == 2 &&
                gameUtils.validateBet(player.getBet(), player.getFund().getValue());
    }


}
