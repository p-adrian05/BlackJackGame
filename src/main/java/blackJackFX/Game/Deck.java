package blackJackFX.Game;

import java.util.Collections;
import java.util.List;

public interface Deck {

    List<Card> getDeckCards();
    
    default void shuffle(){
        Collections.shuffle(getDeckCards());
    }



}
