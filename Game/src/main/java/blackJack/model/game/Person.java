package blackJack.model.game;

import blackJack.model.Model;
import blackJack.model.card.Card;
import blackJack.model.card.Deck;

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
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    public int getCardsSumValues(){
       return Model.getInstance().getDeck().calcCardsSumValue(this.cards);
    }

    public List<Card> getCards() {
        return this.cards;
    }
}
