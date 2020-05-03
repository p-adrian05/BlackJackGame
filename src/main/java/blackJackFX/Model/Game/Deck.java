package blackJackFX.Model.Game;

import java.util.Collections;
import java.util.List;

public interface Deck {

    List<Card> getDeckCards();
    int calcCardsSumValue(List<Card> cards);
    default void shuffle(){
        Collections.shuffle(getDeckCards());
    }



}
