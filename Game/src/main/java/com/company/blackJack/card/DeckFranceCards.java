package com.company.blackJack.card;

import lombok.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class represents an implementation of {@link Deck} interface.
 */
@AllArgsConstructor
@NoArgsConstructor
public class DeckFranceCards implements Deck{

    /**
     * The list of {@link FranceCard} objects.
     */
    private LinkedList<FranceCard> cards;

    @Override
    public List<Card> getDeckCards() {
        return Collections.unmodifiableList(this.cards);
    }

    /**
     * Returns a {@link Card} object from the {@link #cards} list and remove it.
     *
     * @return a {@link Card} object
     */
    @Override
    public Card getCard() {
        return this.cards.removeFirst();
    }

    /**
     * Calculates the summary value of a list of {@link FranceCard} objects
     * implemented {@link Card} interface.
     *
     * @param cards {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int calcCardsSumValue(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            if (card.getValue().equals("ACE") && sum > 10) {
                sum++;
            } else {
                sum += card.getIntValue();
            }
        }
        return sum;
    }

    @Override
    public void shuffle() {
        Collections.shuffle(this.cards);
    }
}



