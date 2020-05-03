package blackJackFX.Model.Game;

import java.util.LinkedList;
import java.util.List;

public class DeckFranceCards implements Deck{

    private LinkedList<FranceCard> cards;

    @Override
    public List<Card> getDeckCards() {
        return new LinkedList<>(this.cards);
    }

    @Override
    public Card getCard() {
        return this.cards.removeFirst();
    }

}



