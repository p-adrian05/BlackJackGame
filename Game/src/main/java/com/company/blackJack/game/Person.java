package com.company.blackJack.game;

import com.company.blackJack.card.Card;
import com.company.blackJack.card.DeckFranceCards;

import java.util.List;

/**
 * Person interface class representing a person int the game.
 */
public interface Person {

     void addCard(Card card);
     List<Card> getCards();

}
