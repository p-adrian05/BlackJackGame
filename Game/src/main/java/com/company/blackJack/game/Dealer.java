package com.company.blackJack.game;


import com.company.blackJack.card.Card;
import lombok.NonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a kind of {@link Person} object.
 */

public class Dealer implements Person{

    private final List<Card> cards;

    public Dealer() {
        cards = new LinkedList<>();
    }

    @Override
    public void addCard(@NonNull Card card) {
        cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
