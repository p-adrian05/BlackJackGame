package com.company.blackJack.card;

import java.util.List;

/**
 * The Deck interface provides two methods to get implementations of {@link Card} interface.
 *
 * The Deck interface provides one method to calculate list of implementations of {@link Card} interface.
 */

public interface Deck {

    List<Card> getDeckCards();
    Card getCard();

}
