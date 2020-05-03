package blackJackFX.Model.Game;

import java.util.LinkedList;
import java.util.List;

public abstract class Person {

    private int cardsSumValues;
    private List<Card> cards;

    protected Person(){
        cards = new LinkedList<>();
        cardsSumValues = 0;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
    public int getCardsSumValues() {
        return this.cardsSumValues;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setCardsSumValues(int cardsSumValues) {
        this.cardsSumValues = cardsSumValues;
    }

}
