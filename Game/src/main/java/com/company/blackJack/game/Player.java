package com.company.blackJack.game;

import com.company.blackJack.card.Card;
import javafx.beans.property.IntegerProperty;

import java.util.List;
/**
 * Person interface class representing a player int the game.
 */
public interface Player extends Person{

    /**
     * Increases the value of a bet attribute and extract it from the fund.
     * @param bet the value to add and extract
     */
    void addBetFromFund(int bet);
    /**
     * Returns whether it is possible to split cards.
     *
     * @return {@code true} if two elements have in a list where {@link Card} objects are
     * and the elements' values equals, {@code false} otherwise
     */
    boolean isEnableSplitCards();
    /**
     * Splitting the cards,this case on two elements are there in a list,
     * removes a card from them list where {@link Card} objects are
     * and add to another list.
     */
    void madeSplitCards();
    /**
     * Means that the player has a second place where the cards can load.
     * It makes sense in split mode after stand for the first hand.
     */
    void madeSecondHand();
    IntegerProperty getFund();
    int getBet();
    List<Card> getSplitCards();
}
