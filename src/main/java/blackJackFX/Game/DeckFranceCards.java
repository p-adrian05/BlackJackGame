package blackJackFX.Game;

import java.util.LinkedList;
import java.util.List;

public class DeckFranceCards implements Deck{

    private List<FranceCard> cards;

    @Override
    public List<Card> getDeckCards() {
        return new LinkedList<>(this.cards);
    }


}



