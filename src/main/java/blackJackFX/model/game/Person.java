package blackJackFX.model.game;

import blackJackFX.model.Model;

import java.util.List;

/**
 * Person abstract class representing a person who play in the game.
 */

public abstract class Person {

    protected List<Card> cards;

    protected void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns the summary value of the {@link #cards} list
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    protected int getCardsSumValues(){
       return Model.getInstance().getDeck().calcCardsSumValue(this.cards);
    }

    protected List<Card> getCards() {
        return this.cards;
    }

    protected void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
