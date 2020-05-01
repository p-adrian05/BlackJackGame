package blackJackFX.Model;

import java.util.List;

public class Cards implements Deck{

    private List<Card> cards;
    private String deck_id;

    @Override
    public List<Card> getDeckCards() {
        return this.cards;
    }

    @Override
    public String getDeck_id() {
        return this.deck_id;
    }

}



