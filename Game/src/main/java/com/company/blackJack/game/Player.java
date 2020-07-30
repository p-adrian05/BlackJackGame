package com.company.blackJack.game;

import com.company.blackJack.card.Card;
import javafx.beans.property.IntegerProperty;

import java.util.List;
/**
 * Person interface class representing a player int the game.
 */
public interface Player extends Person{

    void addBetFromFund(int bet);
    void addFund(int fund);
    boolean isEnableSplitCards();
    void madeSplitCards();
    void madeSecondHand();
    IntegerProperty getFund();
    int getBet();
    List<Card> getSplitCards();

}
