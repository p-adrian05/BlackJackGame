package com.company.blackJack;

import com.company.blackJack.card.Card;
import com.company.blackJack.card.Deck;
import com.company.blackJack.game.*;
import javafx.beans.property.IntegerProperty;

import java.util.List;
/**
 * Interface collects all functionality needed for the controllers.
 */
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
    String getResultsString();

    /**
     * Returns the final prize of the player using {@link GameUtils#calculatePrizes(int, Result[])}.
     *
     * @return an array of int prizes
     */
     int[] getPrizes();

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

     IntegerProperty getPlayerFund();

    /**
     * Add a {@link Card} object to a {@link Person} implementation class.
     * @param person a class which implements {@link Person} interface.
     * @return the {@link Card} object which has been added to the {@link Person} implementation class.
     */
     Card loadCardToPerson(Class<? extends Person> person);

    /**
     * If available to made split cards in the {@link Player} class, enables it.
     *
     * @return {@code true} if If available to made split cards, {@code false} otherwise.
     */
     boolean enablePlayerSplitCards();

    /**
     * Add the given param int value to a {@link Player} class.
     * @param bet given int value which is added to {@link Player} class.
     * @return {@code true} if the given param is valid, {@code false} otherwise.
     */
     boolean addPlayerBet(int bet);

    /**
     * Enables second hand mode to a {@link Player} implementation.
     * For more details: {@link Player#madeSecondHand()}
     */
     void addSecondHandToPlayer();

    /**
     * Checks if double mode is enable in any state of the game. Means to double the {@link Player}'s bet.
     * @return {@code true} if it is possible to double the bet values from the {@link Player} class,
     * {@code false} otherwise.
     */
    boolean isDoubleEnable();
}
