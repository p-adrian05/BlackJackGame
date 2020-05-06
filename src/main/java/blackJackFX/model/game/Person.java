package blackJackFX.model.game;

import blackJackFX.model.Model;

import java.util.List;

public abstract class Person {

    protected List<Card> cards;

    public void addCard(Card card) {
        cards.add(card);
    }
    public int getCardsSumValues(){
       return Model.getInstance().getDeck().calcCardsSumValue(this.cards);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
