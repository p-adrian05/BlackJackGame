package com.company.blackJack.game;

import com.company.blackJack.card.Card;
import com.company.blackJack.card.DeckFranceCards;

import java.util.List;

/**
 * Person abstract class representing a person who play in the game.
 */

public interface Person {

     void addCard(Card card);
     List<Card> getCards();

}
