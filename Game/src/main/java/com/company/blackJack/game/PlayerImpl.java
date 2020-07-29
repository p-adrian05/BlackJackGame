package com.company.blackJack.game;

import com.company.blackJack.card.Card;
import com.company.blackJack.card.DeckFranceCards;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a kind of {@link Person} object and add more functionality beyond it.
 */
public class PlayerImpl implements Player {

    private final IntegerProperty fund;
    private int bet;
    private final List<Card> splitCards;
    private final List<Card> cards;
    private int hands;

    /**
     * Creates a {@code Player} object, initialize attributes.
     */
    public PlayerImpl() {
        fund = new SimpleIntegerProperty(0);
        bet = 0;
        cards = new LinkedList<>();
        splitCards = new LinkedList<>();
        hands = 1;
    }

    /**
     * Sets {@link #hands} attribute value to 2, means that the player has a second place where the cards can load.
     * It makes sense in split mode after stand for the first hand.
     */
    @Override
    public void madeSecondHand(){
        this.hands = 2;
    }

    @Override
    public IntegerProperty getFund() {
        return this.fund;
    }

    @Override
    public int getBet() {
        return this.bet;
    }

    @Override
    public List<Card> getSplitCards() {
        return this.splitCards;
    }

    /**
     * Increases the value of {@link #bet} attribute and extract it from the {@link #fund}.
     * @param bet the value to add and extract
     */
    @Override
    public void addBetFromFund(int bet) {
        this.bet += bet;
        this.fund.set(fund.subtract(bet).get());
    }
    @Override
    public void addFund(int fund) {
        this.fund.set(this.fund.add(bet).get());
    }
    /**
     * Returns the summary value of the {@link #splitCards} list
     * calling the {@link DeckFranceCards#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    @Override
    public int getCardsSumValuesSplit(){
        return DeckFranceCards.calcCardsSumValue(this.splitCards);
    }
    /**
     * Returns whether it is possible to split cards.
     *
     * @return {@code true} if two cards has in the {@link #cards} list
     * and the elements' values equals, {@code false} otherwise
     */
    @Override
    public boolean isEnableSplitCards(){
        if(cards.size()==2){
            return cards.get(0).getIntValue() == cards.get(1).getIntValue();
        }
        return false;
    }
    /**
     * Splitting the cards, remove a card form {@link #cards} lists
     * and add to {@link #splitCards} list.
     */
    @Override
    public void madeSplitCards(){
        if(isEnableSplitCards()){
            splitCards.add(cards.remove(1));
        }
    }
    /**
     * Adds a {@link Card} object to a list.
     *
     * @param card {@link Card} object added to a list or to another
     *                         if card splitting enabled
     */
    @Override
    public void addCard(Card card) {
        if(this.hands==2){
                splitCards.add(card);
        }else{
            cards.add(card);
        }
    }

    /**
     * Returns the summary value of the {@link Card} objects list
     * calling the {@link DeckFranceCards#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    @Override
    public int getCardsSumValues() {
        return DeckFranceCards.calcCardsSumValue(cards);
    }

    @Override
    public List<Card> getCards() {
        return this.cards;
    }
}