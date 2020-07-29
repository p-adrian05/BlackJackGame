package com.company.blackJack.game;

import com.company.blackJack.card.Card;
import com.company.blackJack.card.DeckFranceCards;

import java.util.List;

/**
 * Person abstract class representing a person who play in the game.
 */

public abstract class Person {

    protected List<Card> cards;

    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns the summary value of the {@link #cards} list
     * calling the {@link DeckFranceCards#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    public int getCardsSumValues(){
       return DeckFranceCards.calcCardsSumValue(this.cards);
    }

    public List<Card> getCards() {
        return this.cards;
    }
}
