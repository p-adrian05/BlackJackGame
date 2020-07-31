package com.company.blackJack.game;


import com.company.blackJack.card.Card;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a kind of {@link Person} object.
 */

public class Dealer implements Person{

    @Getter
    private final List<Card> cards;

    public Dealer() {
        cards = new LinkedList<>();
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

}
