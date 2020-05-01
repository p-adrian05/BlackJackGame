package blackJackFX.Model;

import java.util.Collections;
import java.util.List;

public interface Deck {

    List<Card> getDeckCards();
    String getDeck_id();

    default void shuffle(){
        Collections.shuffle(getDeckCards());
    }



}
