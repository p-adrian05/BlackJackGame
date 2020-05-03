package blackJackFX.Model.Game;

import java.util.Collections;
import java.util.List;

public interface Deck {

    List<Card> getDeckCards();
    Card getCard();
    default void shuffle(){
        Collections.shuffle(getDeckCards());
    }



}
