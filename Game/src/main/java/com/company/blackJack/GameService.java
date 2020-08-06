package com.company.blackJack;

import com.company.blackJack.card.Card;
import com.company.blackJack.card.Deck;
import com.company.blackJack.game.*;
import javafx.beans.property.IntegerProperty;

import java.util.List;

public interface GameService {
     /**
      * Set the value of funds in a{@link Player} object.
      */
     void setPlayerFund(int fund);

    /**
     * Makes new instances of {@link Player}, {@link Dealer} and {@link Deck} objects
     * for making a new game.
     */
     void resetGame();
    /**
     * Returns whether the actual game is over checking the scores.
     *
     * @return {@code true} if the {@link Player} score(s) pass a specified value, {@code false} otherwise
     */
     boolean isGameOver();

    /**
     * Returns whether the actual game is over in {@link Player#isEnableSplitCards()} case.
     *
     * @return {@code true} if the {@link Player} first score not pass 21 value and the second pass 21, {@code false} otherwise
     */
     boolean isSplitGameOver();

    /**
     * Returns the final results of the game using {@link GameUtils#calculateResult(int, int, int)} function.
     *
     * @return an array of {@link Result} enum values
     */
     Result[] getResults();

    /**
     * Returns the final prize of the player using {@link GameUtils#calculatePrizes(int, Result[])}.
     *
     * @param results an array of {@link Result} enum values
     * @return an array of int prizes
     */
     int[] getPrizes(Result[] results);

    /**
     * Sets user entity's attributes and save it to the database.
     */
     void saveUser();
    /**
     * Returns the summary value of the {@link Player }{@link Card} objects list
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
     int getPlayerCardsValue();
    /**
     * Returns the summary value of the {@link Player } {@link Card} objects list,
     * which contains the split cards,
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
     int getPlayerSplitCardsValue();
    /**
     * Returns the summary value of the {@link Dealer } {@link Card} objects list,
     * which contains the split cards,
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
     int getDealerCardsValue();

     void setGameDataId(Long id);

     String getUsername();

     void setUsername(String username);

     Long getGameDataId();

     Card loadCardToPerson(Class<? extends Person> person);

     boolean enablePlayerSplitCards();
     boolean addPlayerBetFromFund(int funds);
     boolean addSecondHandToPlayer();
     IntegerProperty getPlayerFund();
     boolean isDoubleEnable();
}
