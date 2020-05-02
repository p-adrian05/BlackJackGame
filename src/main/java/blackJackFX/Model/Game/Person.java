package blackJackFX.Game;

import java.util.List;

public abstract class Person {

    private int cardsSumValues;
    private List<Card> cards;

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
